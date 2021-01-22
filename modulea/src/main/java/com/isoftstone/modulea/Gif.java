package com.isoftstone.modulea;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Image;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gif extends Image{

    private List<PixelMap> pixelMapList=new ArrayList<>();

    private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "GifImage");
    // 动画
    private AnimatorValue animatorValue;
    private ImageSource imageSource;
    private Context context;
    private String path;
    private int ids;
    public Gif(Context context) throws IOException, NotExistException, WrongTypeException {
        super(context);
        this.context=context;
        ResourceManager resourceManager =context.getResourceManager();
        init(resourceManager);
    }

    public Gif(Context context, AttrSet attrSet) throws IOException, NotExistException, WrongTypeException {
        super(context, attrSet);
        this.context=context;
        String id  = attrSet.getAttr("image_src").get().getStringValue();
        // $media:16777218
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(id);
        String all = matcher.replaceAll("");
        ids = Integer.valueOf(all);
        ResourceManager resourceManager = context.getResourceManager();
        //01-20 16:04:19.972 13519-13519/com.isoftstone.mygifview I 00110/GifImage: path数据$media:16777218entry/resources/base/media/deleting.gif
        // HiLog.info(label, "path数据值"+resourceManager.getMediaPath(ids));
        init(resourceManager);
    }

    private int index = 0;
    private    int i;
    // 动画侦听函数
    private final AnimatorValue.ValueUpdateListener mAnimatorUpdateListener
            = new AnimatorValue.ValueUpdateListener() {
        @Override
        public void onUpdate(AnimatorValue animatorValue, float v) {
            index++;
            setPixelMap(pixelMapList.get((int)(v*pixelMapList.size())));
            // bofang index tupian
            //PixelMap[index];
            invalidate();
        }
    };

    private void init(ResourceManager resourceManager) throws IOException, NotExistException, WrongTypeException {
        //resourceManager.ResourceTable.Media_deleting);
        //String name = resourceManager.getMediaPath(getId());
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
        //  decodingOptions.desiredSize = new Size(390, 320);
        decodingOptions.allowPartialImage=true;
        sourceOptions.formatHint="image/gif";
        RawFileEntry rawFileEntry = resourceManager.getRawFileEntry(resourceManager.getMediaPath(ids));
        imageSource = ImageSource.create(rawFileEntry.openRawFile(),sourceOptions);
      //resourceManager.getElement().
        HiLog.info(label, "path数据值"+resourceManager.getResource(ids)+imageSource.getImageInfo(1));
        if (imageSource != null) {
            i=0;
            while(imageSource.createPixelmap(i,decodingOptions)!=null) {
                pixelMapList.add(imageSource.createPixelmap(i, decodingOptions));
                i++;
                //   timage.setPixelMap(imageSource.createPixelmap(i,decodingOptions));
                // frameAnimationElement.addFrame(timage.getImageElement(),100);
            }
        }
        // 启动动画
        //   this.se;
        animatorValue = new AnimatorValue();
        animatorValue.setCurveType(Animator.CurveType.LINEAR);
        animatorValue.setDelay(100);
        animatorValue.setLoopedCount(Animator.INFINITE);
        animatorValue.setDuration(i/3*100);
        animatorValue.setValueUpdateListener(mAnimatorUpdateListener);
        animatorValue.start();
    }


}
