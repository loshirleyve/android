package com.yun9.mobile.camera.factory;

import android.content.Context;

import com.yun9.mobile.camera.interfaces.A4FragmentNetDocument;
import com.yun9.mobile.camera.interfaces.presenter.Pre4uiNetDocumentFragment;
import com.yun9.mobile.camera.interfaces.presenter.Ui4PreNetDocumentFragment;

public abstract class FactoryPresenter {

	public abstract Pre4uiNetDocumentFragment creatPre4uiNetDocumentFragment(Ui4PreNetDocumentFragment ui, Context context, A4FragmentNetDocument a4Fragment);
}
