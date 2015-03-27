package com.yun9.mobile.framework.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.adapter.SignListAdapter;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.ScheDulingWork;
import com.yun9.mobile.framework.personelservice.CheckOnWorkAttendFactory;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.view.TitleBarView;
import com.yun9.mobile.position.iface.IAcquirePosition;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.impl.AcquirePosition;
import com.yun9.mobile.position.impl.PositionFactory;

public class SignActivity extends Activity implements AdapterView.OnItemClickListener
{

    private ListView lv_sign;
    private SignListAdapter mAdapter;
    private List<ScheDulingWork> scheDulingWorks;
    private TitleBarView titleView;
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        lv_sign = (ListView) findViewById(R.id.lv_sign);
        titleView = (TitleBarView) findViewById(R.id.sign_title);
        TextView tvTitle = titleView.getTvTitle();
        ImageButton btn_back = titleView.getBtnLeft();

        lv_sign.setOnItemClickListener(this);
        tvTitle.setText(R.string.sign);
        tvTitle.setVisibility(View.VISIBLE);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SignActivity.this.finish();
                Intent intent = new Intent(getApplicationContext(), DaKaActiviy.class);
    		    startActivity(intent);
            }
        });

        showJob();
    }

    private void showJob()
    {
        CheckOnWorkAttendFactory factory = BeanConfig.getInstance().getBeanContext().get(CheckOnWorkAttendFactory.class);

        long workdate = date.getTime();
        SessionManager manager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        String userId = manager.getAuthInfo().getUserinfo().getId();
        String instId = manager.getAuthInfo().getInstinfo().getId();
        CheckOnWorkAttendFactory.ScheDulingWorkInParm inputParm = new CheckOnWorkAttendFactory.ScheDulingWorkInParm(workdate, userId, instId);
        factory.getSchedulingWork(inputParm , new AsyncHttpResponseCallback() {

            @Override
            public void onSuccess(Response response) {
                scheDulingWorks = (List<ScheDulingWork>) response.getPayload();
                if (scheDulingWorks != null && scheDulingWorks.size() > 0)
                {
                    lv_sign.setVisibility(View.VISIBLE);
                    mAdapter = new SignListAdapter(SignActivity.this, scheDulingWorks);
                    lv_sign.setAdapter(mAdapter);
                }else
                {
                    Toast.makeText(SignActivity.this,"今天没有班次",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(SignActivity.this,"获取班次失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l)
    {
        if (scheDulingWorks.get(i).getChecked())
        {
            Toast.makeText(SignActivity.this,"已经成功签到",Toast.LENGTH_SHORT).show();
            return;
        }

        final IAcquirePosition defaultIAPosition = PositionFactory.createPosition(SignActivity.this);
        defaultIAPosition.go2GetPosition(AcquirePosition.MOD_DAKA, AcquirePosition.RADUIS_DEFAULT, new IAcquirePositionCallBack()
        {
            @Override
            public void onSuccess(final OutParam outParm)
            {
                if (!TextUtils.isEmpty(outParm.getAddr()))
                {
                    final SimpleDateFormat format1 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    final String signData = format1.format(date);
                    final AlertDialog dialog = new AlertDialog.Builder(SignActivity.this).create();
                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setContentView(R.layout.dialog_sign);
                    TextView tv_sign_time = (TextView) window.findViewById(R.id.tv_sign_time);
                    TextView tv_sign_addr = (TextView) window.findViewById(R.id.tv_sign_addr);
                    Button btn_sign_commit = (Button) window.findViewById(R.id.btn_sign_commit);
                    Button btn_sign_cancal = (Button) window.findViewById(R.id.btn_sign_cancel);

                    tv_sign_time.setText("签到时间：" + signData);
                    tv_sign_addr.setText("签到地点：" + outParm.getAddr());

                    btn_sign_commit.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            final long time = date.getTime();
                            CheckOnWorkAttendFactory factory = BeanConfig.getInstance().getBeanContext().get(CheckOnWorkAttendFactory.class);
                            long workdate = scheDulingWorks.get(i).getWorkdate();
                            String instid = scheDulingWorks.get(i).getInstid();
                            String userid = scheDulingWorks.get(i).getUserid();
                            String shiftid = scheDulingWorks.get(i).getShiftid();
                            Long checkdatetime = time;
                            String checklocationx = String.valueOf(outParm.getLatitude());
                            String checklocationy = String.valueOf(outParm.getLongitude());
                            String checklocationlabel = outParm.getAddr();
                            String createby = "186";
                            CheckOnWorkAttendFactory.CheckInInParm InputParm = new CheckOnWorkAttendFactory.CheckInInParm(instid, userid, workdate, shiftid, checkdatetime, checklocationx, checklocationy, checklocationlabel, createby);
                            factory.CheckIn(InputParm, new AsyncHttpResponseCallback()
                            {

                                @Override
                                public void onSuccess(Response response)
                                {
                                    Toast.makeText(SignActivity.this, "打卡成功", Toast.LENGTH_SHORT).show();
                                    scheDulingWorks.get(i).setActualcheckdatetime(time);
                                    scheDulingWorks.get(i).setActuallocationlabel(outParm.getAddr());
                                    scheDulingWorks.get(i).setChecked(true);
                                    mAdapter.notifyDataSetChanged();
                                    dialog.cancel();
                                }

                                @Override
                                public void onFailure(Response response)
                                {
                                    Toast.makeText(SignActivity.this, "打卡失败：" + response.getCause(), Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            });
                        }
                    });

                    btn_sign_cancal.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            dialog.cancel();
                        }
                    });

                }
            }

            @Override
            public void onFailure()
            {
            }
        });
    }
}
