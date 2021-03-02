package com.example.seekbar_harmony;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.ProgressBar;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.multimodalinput.event.TouchEvent;

public class SeekBarComponent extends ProgressBar implements Component.DrawTask, Component.TouchEventListener {

    Context mContext;
    Paint paint;
    float moveX = 0;
    int progressValue = 0;

    float moveDistance = 0;


    public SeekBarComponent(Context context) {
        this(context, null);
    }

    public SeekBarComponent(Context context, AttrSet attrSet) {
        this(context, attrSet, "");
    }

    public SeekBarComponent(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        mContext = context;
        setTouchEventListener(this);
        addDrawTask(this::onDraw);
        ini();

    }

    private void ini() {
        paint = new Paint();
        setMaxValue(100);
    }

    public void SeekProgress(int progress) {
        this.progressValue = progress;
        moveDistance = getWidth() * (progress / 100f);
//        System.out.println("------- getWidth()-------" + getWidth() + "==" + moveDistance);
        invalidate();
    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        int height = component.getHeight();
        paint.setColor(Color.RED);
        canvas.drawCircle(moveDistance + 30, height / 2, 30, paint);
        setProgressValue(progressValue);
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        changeListener.isTouch(true);
        int componentWidth = component.getWidth();
        final boolean pointerUp =
                touchEvent.getAction() == TouchEvent.OTHER_POINT_UP;
        final int skipIndex = pointerUp ? touchEvent.getIndex() : -1;
        float sumX = 0;
        final int count = touchEvent.getPointerCount();
        // 把所有还在触摸的手指的位置x，y加起来，后面求平均数，算出中心焦点
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) {
                // 跳过非主要指针的抬起动作
                continue;
            }
            sumX += touchEvent.getPointerPosition(i).getX();
        }
        final int div = pointerUp ? count - 1 : count;
        // 求平均值，算出中心焦点
        float endUpX = sumX / div - component.getLeft() - 15;

        progressValue = (int) (endUpX / componentWidth * 100);
        System.out.println("-------progressValue-------" + progressValue);

        switch (touchEvent.getAction()) {
            case TouchEvent.CANCEL:
                System.out.println("-------指示事件被中断或取消。。-------");
                break;
            case TouchEvent.PRIMARY_POINT_UP:
                changeListener.isTouch(false);
                changeListener.onProgressChanged(progressValue);
                invalidate();
                break;
            case TouchEvent.PRIMARY_POINT_DOWN:
                //表示第一根手指触摸屏幕。
                moveDistance = moveX = endUpX;
                break;
            case TouchEvent.POINT_MOVE:
                float scrollY = endUpX - moveX;
                moveDistance += scrollY;
                moveX = endUpX;
                invalidate();
                break;
        }
        return true;
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        changeListener = listener;
    }

    OnSeekBarChangeListener changeListener;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(int progress);
        void isTouch(boolean isTouch);
    }
}
