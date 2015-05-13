package com.yun9.jupiter.view;

import android.os.Bundle;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

/**
 * Created by Leon on 15/4/27.
 */
public abstract class JupiterFragmentMenuActivity extends JupiterBaseFragmentActivity{

    protected abstract int getMenuContentView();

    protected abstract Position getMenuPosition();

    protected abstract MenuDrawer.Type getMenuType();

    protected MenuDrawer mMenuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMenuDrawer = MenuDrawer.attach(this, this.getMenuType(), this.getMenuPosition(), MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setContentView(this.getContentView());
        mMenuDrawer.setMenuView(this.getMenuContentView());

        mMenuDrawer.peekDrawer();
    }

}

