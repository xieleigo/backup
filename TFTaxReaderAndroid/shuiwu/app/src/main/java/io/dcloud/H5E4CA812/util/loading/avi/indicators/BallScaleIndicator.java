package io.dcloud.H5E4CA812.util.loading.avi.indicators;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import android.animation.ValueAnimator;
import io.dcloud.H5E4CA812.util.loading.avi.Indicator;

import java.util.ArrayList;

/**
 * Created by Jack on 2015/10/19.
 */
public class BallScaleIndicator extends Indicator {

    float scale=1;
    int alpha=255;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing=4;
        paint.setAlpha(alpha);
        canvas.scale(scale,scale,getWidth()/2,getHeight()/2);
        paint.setAlpha(alpha);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-circleSpacing,paint);
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators=new ArrayList<>();
        ValueAnimator scaleAnim=ValueAnimator.ofFloat(0,1);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator alphaAnim=ValueAnimator.ofInt(255, 0);
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.setDuration(1000);
        alphaAnim.setRepeatCount(-1);
        addUpdateListener(alphaAnim,new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animators.add(scaleAnim);
        animators.add(alphaAnim);
        return animators;
    }

    /**
     * Created by Jack on 2015/10/19.
     */
    public static class BallTrianglePathIndicator extends Indicator {

        float[] translateX=new float[3],translateY=new float[3];

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            for (int i = 0; i < 3; i++) {
                canvas.save();
                canvas.translate(translateX[i], translateY[i]);
                canvas.drawCircle(0, 0, getWidth() / 10, paint);
                canvas.restore();
            }
        }

        @Override
        public ArrayList<ValueAnimator> onCreateAnimators() {
            ArrayList<ValueAnimator> animators=new ArrayList<>();
            float startX=getWidth()/5;
            float startY=getWidth()/5;
            for (int i = 0; i < 3; i++) {
                final int index=i;
                ValueAnimator translateXAnim=ValueAnimator.ofFloat(getWidth()/2,getWidth()-startX,startX,getWidth()/2);
                if (i==1){
                    translateXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,getWidth()/2,getWidth()-startX);
                }else if (i==2){
                    translateXAnim=ValueAnimator.ofFloat(startX,getWidth()/2,getWidth()-startX,startX);
                }
                ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,getHeight()-startY,getHeight()-startY,startY);
                if (i==1){
                    translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,startY,getHeight()-startY);
                }else if (i==2){
                    translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,startY,getHeight()-startY,getHeight()-startY);
                }

                translateXAnim.setDuration(2000);
                translateXAnim.setInterpolator(new LinearInterpolator());
                    translateXAnim.setRepeatCount(-1);
                addUpdateListener(translateXAnim,new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        translateX [index]= (float) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });

                translateYAnim.setDuration(2000);
                translateYAnim.setInterpolator(new LinearInterpolator());
                translateYAnim.setRepeatCount(-1);
                addUpdateListener(translateYAnim,new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        translateY [index]= (float) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });

                animators.add(translateXAnim);
                animators.add(translateYAnim);
            }
            return animators;
        }


    }
}
