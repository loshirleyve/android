package com.yun9.mobile.position.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.yun9.mobile.R;


public class DefineKeyPositionFragment extends PositionFragment {
	protected static final String TAG = DefineKeyPositionFragment.class.getSimpleName();
	private EditText edtDefineKey;
	private String SEARCH_KEY_DEFAULT = "茂业";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_position_definekey, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		edtDefineKey = (EditText) getView().findViewById(R.id.edtDefineKey);
	}
	
	@Override
	public String getSearKey() {
		String searKey = edtDefineKey.getText().toString();
		if(!searKey.equals(""))
		{
			return searKey;
		}
		return SEARCH_KEY_DEFAULT;
		
	}


}
