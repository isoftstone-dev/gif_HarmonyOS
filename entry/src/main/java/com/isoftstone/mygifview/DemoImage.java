package com.isoftstone.mygifview;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Image;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.media.image.ImageSource;

public class DemoImage extends Image implements Runnable{
    ImageSource.DecodingOptions decodingOptions = new ImageSource.DecodingOptions();
    private ImageSource i;
    private Thread thread;
   // private EventHandler handler=new EventHandler();


    public DemoImage(Context context) {
        super(context);
       // this.i=imageSource;
    }

    public DemoImage(Context context, AttrSet attrSet) {
        super(context, attrSet);
    }


    private final Runnable updateResults = new Runnable() {
        @Override
        public void run() {
          //  if (tmpBitmap != null && !tmpBitmap.isRecycled()) {
             //   setPixelMap(tmpBitmap);
           // }
        }
    };
    @Override
    public void run() {

    }
}
