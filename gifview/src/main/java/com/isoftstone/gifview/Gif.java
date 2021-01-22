package com.isoftstone.gifview;


import java.awt.*;

public class Gif extends Image{

    // 动画侦听函数
    private final AnimatorValue.ValueUpdateListener mAnimatorUpdateListener
            = new AnimatorValue.ValueUpdateListener() {
        @Override
        public void onUpdate(AnimatorValue animatorValue, float v) {

            index++;
            if (index >= 3) {
                index = 0;
            }

            PixelMap(index);
            invalidate();
        }
    };


    private void init()  {
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
