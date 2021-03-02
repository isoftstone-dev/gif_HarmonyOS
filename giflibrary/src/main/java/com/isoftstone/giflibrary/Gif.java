package com.isoftstone.giflibrary;

import com.isoftstone.giflibrary.decoder.GifDecoder;
import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Image;
import ohos.app.Context;
import ohos.global.resource.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gif extends Image{

    private List<PixelMap> pixelMapList=new ArrayList<>();

    private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "GifImage");
    // 动画
    private AnimatorValue animatorValue;
    private ImageSource imageSource;
    private GifDecoder gifDecoder;
    private Boolean ispaused=false;
    private int duration;
    private float speed=1;
    public void setSpeed(float speed1) {
        float EPSINON = (float) 0.00001;
        if(speed1> EPSINON) {
            this.speed = speed1;
        }
        HiLog.info(label, "speed数据值"+ (speed));
        animatorValue.stop();
        animatorValue.setDuration((long) (speed*duration));
        animatorValue.start();
        invalidate();
    }
    public Gif(Context context) {
        super(context);
    }

    public Gif(Context context, AttrSet attrSet) throws IOException, NotExistException, WrongTypeException {
        super(context, attrSet);
        gifDecoder=new GifDecoder();
        ResourceManager resourceManager = context.getResourceManager();
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        sourceOptions.formatHint="image/gif";

        if(attrSet.getAttr("image_src").isPresent()) {
            String id = attrSet.getAttr("image_src").get().getStringValue();
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(id);
            String all = matcher.replaceAll("");
            RawFileEntry rawFileEntry = resourceManager.getRawFileEntry(resourceManager.getMediaPath(Integer.parseInt(all)));
            ImageSource imageSource = ImageSource.create(rawFileEntry.openRawFile(), sourceOptions);
            gifDecoder.read(rawFileEntry.openRawFile(), (int) rawFileEntry.openRawFileDescriptor().getFileSize());

            if (imageSource != null) {
                init(imageSource);
            }
        } else {
            invalidate();
        }

    }


    private    int i;
    // 动画侦听函数
    private final AnimatorValue.ValueUpdateListener mAnimatorUpdateListener
            = new AnimatorValue.ValueUpdateListener() {
        @Override
        public void onUpdate(AnimatorValue animatorValue, float v) {
            setPixelMap(pixelMapList.get((int)(v*pixelMapList.size())));
            invalidate();
        }
    };

    private void init(ImageSource imageSource) {
        pixelMapList.clear();
        duration=0;
      //  invalidate();
        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
        decodingOptions.allowPartialImage=true;
            i=1;
            if(gifDecoder.getFrameCount()>0) {
                while (i < gifDecoder.getFrameCount()) {
                    pixelMapList.add(imageSource.createPixelmap(i, decodingOptions));
                    duration += gifDecoder.getDelay(i);
                    i++;
                }
            }else {
                while (imageSource.createPixelmap(i, decodingOptions)!=null) {
                    pixelMapList.add(imageSource.createPixelmap(i, decodingOptions));
                    duration += gifDecoder.getDelay(i);
                    i++;
                }
            }
        // 启动动画
        HiLog.info(label, "持续时间"+duration);
        animatorValue = new AnimatorValue();
        animatorValue.setCurveType(Animator.CurveType.LINEAR);
        animatorValue.setDelay(100);
        animatorValue.setLoopedCount(Animator.INFINITE);
        animatorValue.setDuration(duration==0 ? 3000:duration);
        animatorValue.setValueUpdateListener(mAnimatorUpdateListener);
        animatorValue.start();
    }

    public void load(InputStream is) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(is, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(byte[] data) {
        gifDecoder.read(data);
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(data, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(ByteBuffer data) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(data, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(Callable<RawFileDescriptor> callable) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(callable, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(File file) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(file, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(FileDescriptor fd) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(fd, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void load(String pathName) {
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(pathName, sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }

    public void load(RawFileEntry rawFileEntry) throws IOException {
        gifDecoder.read(rawFileEntry.openRawFile(), (int) rawFileEntry.openRawFileDescriptor().getFileSize());
        ImageSource.SourceOptions sourceOption = new ImageSource.SourceOptions();
        imageSource = ImageSource.create(rawFileEntry.openRawFile(), sourceOption);
        if (imageSource != null) {
            init(imageSource);
        }
    }
    public void pause(){
        if(!ispaused){
            ispaused=true;
        }
        animatorValue.pause();
        invalidate();
    }
    public void play(){
        if(ispaused){
            ispaused=false;
        }
        animatorValue.start();
    }
    public int getDuration(){
        return duration;
    }
    public Boolean isPlaying(){
        return !ispaused;
    }
    public void reset(){
        animatorValue.stop();
        animatorValue.start();
        invalidate();
    }
}
