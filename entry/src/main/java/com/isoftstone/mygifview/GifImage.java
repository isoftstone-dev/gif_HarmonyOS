package com.isoftstone.mygifview;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.render.Canvas;
import ohos.agp.utils.DimensFloat;
import ohos.agp.utils.Rect;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.ResourceManager;
import ohos.global.resource.WrongTypeException;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GifImage extends Image{


    private List<PixelMap> pixelMapList=new ArrayList<>();

    // 动画
    private AnimatorValue animatorValue;
    private ImageSource imageSource;
    // 绘制类
    private LoadingRander loadingRander;

    private Context context;
    private int id=0;
    private String path=null;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLoadingRander(LoadingRander loadingRander) {
        this.loadingRander = loadingRander;
    }

    public GifImage(Context context) throws NotExistException, WrongTypeException, IOException {
        super(context);
        this.context=context;
     //   this.path=getPath();
        ResourceManager resourceManager =context.getResourceManager();
        init(resourceManager,path);

    }

    public GifImage(Context context, AttrSet attrSet) throws NotExistException, WrongTypeException, IOException {
        super(context, attrSet);
        this.context=context;
        ResourceManager resourceManager = context.getResourceManager();
        init(resourceManager,path);
    }

    private int index = 0;

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

    private void init(ResourceManager resourceManager,String path) throws NotExistException, WrongTypeException, IOException {
        //resourceManager.ResourceTable.Media_deleting);
        //String name = resourceManager.getMediaPath(getId());
        ImageSource.SourceOptions sourceOptions = new ImageSource.SourceOptions();
        ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
        //  decodingOptions.desiredSize = new Size(390, 320);
        decodingOptions.allowPartialImage=true;
        sourceOptions.formatHint="image/gif";
        if (path!=null) {
            RawFileEntry rawFileEntry = resourceManager.getRawFileEntry(path+"");
            imageSource = ImageSource.create(rawFileEntry.openRawFile(), sourceOptions);
            // timage=(Image) findComponentById(ResourceTabl.Id_testimg2);
        }
        if (imageSource != null) {
            int i=0;
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
        animatorValue.setDuration(2000);
        animatorValue.setValueUpdateListener(mAnimatorUpdateListener);
        animatorValue.start();
    }


}
