package com.yun9.mobile.framework.dlg.nifty.effects;

import android.view.View;
import com.nineoldandroids.animation.ObjectAnimator;
public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration)

        );
    }
}
