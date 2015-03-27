package com.yun9.mobile.camera.impl;

import java.util.Comparator;

import com.yun9.mobile.camera.domain.DmNetPhoto;
import com.yun9.mobile.framework.model.FileByUserId;

public class ImplComparatorDomainNetAlbum implements Comparator<DmNetPhoto>{

	@Override
	public int compare(DmNetPhoto arg0, DmNetPhoto arg1) {
		
//		DmNetPhoto Domain0 = (DmNetPhoto)arg0;
//		DmNetPhoto Domain1 = (DmNetPhoto)arg1;
		
		FileByUserId file0 = arg0.getFileInfo();
		FileByUserId file1 = arg1.getFileInfo();
		
		Long longfile1 = file1.getCreatedate();
		Long longfile0 = file0.getCreatedate();
		return longfile1.compareTo(longfile0);
	}

}
