package com.yun9.mobile.position.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.yun9.mobile.R;
import com.yun9.mobile.position.iface.IAcquirePosition;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.impl.AcquirePosition;
import com.yun9.mobile.position.impl.PositionFactory;

public class DemoPositionActivity extends Activity {

	private static final String TAG = DemoPositionActivity.class.getSimpleName();
	private final int REQ_POSITION_DEFAULT = 0x1001;
	private final int REQ_POSITION_DAKA = 0x1002;
	

	private Button btnDakaPosition;
	private Button btnDefaultPosition;
    private Button btnDakaPositionCallBack;
	
	IAcquirePosition defaultIAPosition; 
	IAcquirePosition dakaIAPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_position);

		findView();
		init();
		setEvent();
	}

	private void setEvent() {

		btnDakaPosition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dakaIAPosition = PositionFactory.createPosition(DemoPositionActivity.this);
				dakaIAPosition.go2GetPosition(AcquirePosition.MOD_DAKA, AcquirePosition.RADUIS_DEFAULT , REQ_POSITION_DAKA);
			}
		});

		btnDefaultPosition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				defaultIAPosition = PositionFactory.createPosition(DemoPositionActivity.this);
				defaultIAPosition.go2GetPosition(AcquirePosition.MOD_DEFAULT, AcquirePosition.RADUIS_DEFAULT , REQ_POSITION_DEFAULT);
			}
		});

        btnDakaPositionCallBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultIAPosition = PositionFactory.createPosition(DemoPositionActivity.this);
                defaultIAPosition.go2GetPosition(AcquirePosition.MOD_DAKA, AcquirePosition.RADUIS_DEFAULT , new IAcquirePositionCallBack() {
                    @Override
                    public void onSuccess(OutParam outParm) {

                        Log.i(TAG, "onSuccess");
                        btnDakaPositionCallBack.setText("无力吐槽");
                        Toast.makeText(DemoPositionActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                }

                    @Override
                    public void onFailure() {
                        Log.i(TAG, "onFailure");
                        btnDakaPositionCallBack.setText("无力吐槽");
                        Toast.makeText(DemoPositionActivity.this, "onFailure", Toast.LENGTH_SHORT).show();;
                    }
                });
            }
        });
	}

	private void init() {

	}

	private void findView() {
		btnDakaPosition = (Button) findViewById(R.id.btnDakaPosition);
		btnDefaultPosition = (Button) findViewById(R.id.btnDefaultPosition);
        btnDakaPositionCallBack = (Button) findViewById(R.id.btnDakaPositionCallBack);
	}


    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult");


		if(requestCode == REQ_POSITION_DEFAULT){
			IAcquirePositionCallBack.OutParam outParm = defaultIAPosition.getResult(resultCode, data);
			if(outParm != null){
				Log.i(TAG, "REQ_POSITION_DEFAULT ");
				Log.i(TAG, " " + outParm.getAddr());
				Log.i(TAG, " " + outParm.getCity());
				Log.i(TAG, " " + outParm.getLatitude());
				Log.i(TAG, " " + outParm.getLongitude());
			}
		}
		else if(requestCode == REQ_POSITION_DAKA){
			IAcquirePositionCallBack.OutParam outParm = dakaIAPosition.getResult(resultCode, data);
			if(outParm != null){
				Log.i(TAG, "REQ_POSITION_DAKA ");
				Log.i(TAG, " " + outParm.getAddr());
				Log.i(TAG, " " + outParm.getCity());
				Log.i(TAG, " " + outParm.getLatitude());
				Log.i(TAG, " " + outParm.getLongitude());
			}
		}
	
		
		super.onActivityResult(requestCode, resultCode, data);
	}

}
