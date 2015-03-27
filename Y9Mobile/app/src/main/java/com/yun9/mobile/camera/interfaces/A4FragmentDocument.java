package com.yun9.mobile.camera.interfaces;

import com.yun9.mobile.camera.enums.ModeAlbum;

public interface A4FragmentDocument {
	public ModeAlbum getAlbumMode();
	
	public int getMaxChooseDocumentNum();

	public int getSelectableDocumentNum();

	public void notifyAddDocument(int num);

	public void notifyRemoveDocument(int num);
}
