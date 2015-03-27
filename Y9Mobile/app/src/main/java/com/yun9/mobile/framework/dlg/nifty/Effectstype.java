package com.yun9.mobile.framework.dlg.nifty;

import com.yun9.mobile.framework.dlg.nifty.effects.BaseEffects;
import com.yun9.mobile.framework.dlg.nifty.effects.FadeIn;
import com.yun9.mobile.framework.dlg.nifty.effects.FlipH;
import com.yun9.mobile.framework.dlg.nifty.effects.FlipV;
import com.yun9.mobile.framework.dlg.nifty.effects.NewsPaper;
import com.yun9.mobile.framework.dlg.nifty.effects.SideFall;
import com.yun9.mobile.framework.dlg.nifty.effects.SlideLeft;
import com.yun9.mobile.framework.dlg.nifty.effects.SlideRight;
import com.yun9.mobile.framework.dlg.nifty.effects.SlideTop;


/**
 * @author lhk
 *
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(com.yun9.mobile.framework.dlg.nifty.effects.SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(com.yun9.mobile.framework.dlg.nifty.effects.Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(com.yun9.mobile.framework.dlg.nifty.effects.RotateBottom.class),
    RotateLeft(com.yun9.mobile.framework.dlg.nifty.effects.RotateLeft.class),
    Slit(com.yun9.mobile.framework.dlg.nifty.effects.Slit.class),
    Shake(com.yun9.mobile.framework.dlg.nifty.effects.Shake.class),
    Sidefill(SideFall.class);
    private Class effectsClazz;

    private Effectstype(Class mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        try {
            return (BaseEffects) effectsClazz.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
