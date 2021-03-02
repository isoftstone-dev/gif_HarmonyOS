package com.isoftstone.mygifview.slice;


import com.example.seekbar_harmony.SeekBarComponent;
import com.isoftstone.giflibrary.Gif;
import com.isoftstone.mygifview.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;

public class MainAbilitySlice extends AbilitySlice {
   // private GifDecoder gifDecoder;
    private int index = 1;
    private RawFileEntry rawFileEntry;
    private static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x000110, "GifImage");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

    }

    @Override
    public void onActive() {
        super.onActive();
        Gif gifView = (Gif) findComponentById(ResourceTable.Id_testimg);
        Button pause=(Button) findComponentById(ResourceTable.Id_button_gif_pause);
        pause.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                gifView.pause();
            }
        });

        Button play=(Button)findComponentById(ResourceTable.Id_button_gif_play);
        play.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                gifView.play();
            }
        });
        ResourceManager resourceManager = getResourceManager();
    //    gifDecoder=new GifDecoder();
        Button next =(Button)findComponentById(ResourceTable.Id_button_gif_next);
        next.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                switch (index % 10){
                    case 0:
                      rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/deleting.gif");
                        break;
                    case 1:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif1.gif");
                        break;
                    case 2:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif2.gif");
                        break;
                    case 3:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif3.gif");
                        break;
                    case 4:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif4.gif");
                        break;
                    case 5:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif5.gif");
                        break;
                    case 6:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif6.gif");
                        break;
                    case 7:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif7.gif");
                        break;
                    case 8:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif8.gif");
                        break;
                    case 9:
                        rawFileEntry = resourceManager.getRawFileEntry("resources/base/media/gif9.gif");
                        break;
                }
                index++;
                try {
                    gifView.load(rawFileEntry);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        SeekBarComponent seekBar=(SeekBarComponent)findComponentById(ResourceTable.Id_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBarComponent.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(int progress) {
                gifView.pause();
                gifView.setSpeed((progress/100f));
            }

            @Override
            public void isTouch(boolean isTouch) {

            }
        });
    }
    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
