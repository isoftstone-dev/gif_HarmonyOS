package com.isoftstone.mygifview.slice;

import com.isoftstone.modulea.Gif;
import com.isoftstone.mygifview.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
    }

    @Override
    public void onActive() {
        super.onActive();
            Gif image = (Gif) findComponentById(ResourceTable.Id_testimg);
            Gif image1=(Gif) findComponentById(ResourceTable.Id_testimg1);
            Gif gifImage3 = (Gif)findComponentById(ResourceTable.Id_text);

    }
    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
