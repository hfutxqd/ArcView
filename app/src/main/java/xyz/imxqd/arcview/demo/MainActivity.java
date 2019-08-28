package xyz.imxqd.arcview.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import xyz.imxqd.arcview.ArcView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArcView arcView = findViewById(R.id.demo);
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 360f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(3);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcView.setStartAngle((Float) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                float s1 = arcView.getSweepAngle();
                ValueAnimator animator = ObjectAnimator.ofFloat(s1, 360f);
                animator.setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        arcView.setSweepAngle((Float) animation.getAnimatedValue());
                    }
                });
                animator.start();

                ValueAnimator animator2 = ObjectAnimator.ofArgb(arcView.getStartColor(), arcView.getEndColor());
                animator2.setDuration(500);
                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        arcView.setStartColor((int) animation.getAnimatedValue());
                    }
                });
                animator2.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
