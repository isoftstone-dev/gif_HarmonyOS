package com.isoftstone.mygifview;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.render.Canvas;
import ohos.agp.utils.DimensFloat;
import ohos.agp.utils.Rect;
import ohos.app.Context;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.util.ArrayList;
import java.util.List;


public class LoadingRander extends Image {

    protected float mProgress;
    protected float mWidth;
    protected float mHeight;
    protected float mTextSize;
    private List<PixelMap> images=new ArrayList<>();
    public LoadingRander(Context context) {
        super(context);
       // setImages(images);
    }

    public LoadingRander(Context context, AttrSet attrSet) {
        super(context, attrSet);
       // setImages(images);
    }

    public LoadingRander(Context context, List<PixelMap> images) {
        super(context);
        this.images = images;
    }

    public LoadingRander(Context context, AttrSet attrSet, List<PixelMap> images) {
        super(context, attrSet);
        this.images = images;
    }

    // 设置进度
    public void setProgess(float progress) {
        mProgress = progress;
        //invalidate();
        setPixelMap(images.get((int)(progress*images.size())));
    }


    // 绘制
    protected void draw(Canvas canvas, Rect bounds) {
       // DimensFloat pt = getComponentSize();
     //   Rect rect = new Rect(0,0,pt.getSizeXToInt(),pt.getSizeYToInt());
     //   draw(canvas, rect);

    }

    public void setImages(List<PixelMap> images) {
        this.images = images;
    }

}
