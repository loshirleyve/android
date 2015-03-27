package com.maoye.form.utils;

import java.util.List;


public class UtilList {

	public <T> void listAddElement(List<T> src, List<T> dest) {
		if(dest != null && src != null){
			for(T item : dest){
				listAddElement(src, item);
			}
		}
	}
	
	
	public <T> void listAddElement(List<T> src, T item){
		if(item != null && src != null){
			if(!src.contains(item)){
				src.add(item);
			}
		}
	}
}
