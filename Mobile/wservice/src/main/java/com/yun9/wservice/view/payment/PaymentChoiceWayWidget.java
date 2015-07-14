package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Payinfo;

/**
 * 选择付款方式的某一项
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentChoiceWayWidget extends JupiterRelativeLayout implements ISelectable{

    private JupiterRowStyleSutitleLayout sutitleLayout;

    private LinearLayout paymodeDetailLl;

    private EditText editText;

    private TextView paymodeDetailTv;

    private ListView paymodeOptionsLv;

    private JupiterAdapter adapter;

    private Payinfo.PaymodeInfo paymodeInfo;

    private int selectedOptionIndex = -1;

    public PaymentChoiceWayWidget(Context context) {
        super(context);
    }

    public PaymentChoiceWayWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentChoiceWayWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Payinfo.PaymodeInfo paymodeInfo) {
        this.paymodeInfo = paymodeInfo;
        if (paymodeInfo == null){
            sutitleLayout.getMainIV().setVisibility(GONE);
            paymodeDetailLl.setVisibility(GONE);
        } else {
            sutitleLayout.getTitleTV().setText(paymodeInfo.getPaymodeName());
            sutitleLayout.getSutitleTv().setText(paymodeInfo.getDescr());
            paymodeDetailTv.setText(paymodeInfo.getPaymodeTips());
            if (paymodeInfo.getUseAmount() > 0){
                editText.setText(paymodeInfo.getUseAmount()+"");
            }
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
            ImageLoaderUtil.getInstance(this.mContext).displayImage(paymodeInfo.getPaymodeImgid(),sutitleLayout.getMainIV());
        }
    }

    public void showDetail() {
        if (paymodeInfo != null) {
            sutitleLayout.select(true);
            if (hasTicket()){
                paymodeDetailLl.setVisibility(GONE);
                paymodeOptionsLv.setVisibility(VISIBLE);
            } else {
                paymodeDetailLl.setVisibility(VISIBLE);
                paymodeOptionsLv.setVisibility(GONE);
            }
        }
    }

    public void hideDetail() {
        sutitleLayout.select(false);
        paymodeDetailLl.setVisibility(GONE);
        paymodeOptionsLv.setVisibility(GONE);
    }

    public double getUserAmount() {
        // 先判断是不是选择优惠券
        if (selectedOptionIndex > -1){
            return paymodeInfo.getBizFinanceAccounts().get(selectedOptionIndex).getAmount();
        }
        // 获取用户输入
        String ua = editText.getText().toString();
        if (AssertValue.isNotNullAndNotEmpty(ua)){
            return Double.valueOf(ua);
        }
        return 0;
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_payment_choice_way;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.title_layout);
        paymodeDetailLl = (LinearLayout) this.findViewById(R.id.paymode_detail);
        editText = (EditText) this.findViewById(R.id.user_amout_et);
        paymodeDetailTv = (TextView) this.findViewById(R.id.paymode_detail_tv);
        paymodeOptionsLv = (ListView) this.findViewById(R.id.paymode_options_lv);
        sutitleLayout.setSelectMode(true);
        buildView();
    }

    private void buildView() {
        adapter = new JupiterAdapter() {
            @Override
            public int getCount() {
                if (hasTicket()) {
                    return paymodeInfo.getBizFinanceAccounts().size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                JupiterRowStyleTitleLayout titleLayout;
                if (convertView == null){
                    Payinfo.BizFinanceAccount account = paymodeInfo.getBizFinanceAccounts().get(position);
                    titleLayout = new JupiterRowStyleTitleLayout(PaymentChoiceWayWidget.this.mContext);
                    titleLayout.getArrowRightIV().setVisibility(GONE);
                    titleLayout.getMainIV().setVisibility(GONE);
                    titleLayout.getHotNitoceTV().setVisibility(VISIBLE);
                    titleLayout.setSelectMode(true);
                    if (position == 0){
                        selectedOptionIndex = 0;
                        titleLayout.select(true);
                    }
                    titleLayout.getTitleTV().setText(account.getAmount() + "元");
                    titleLayout.getHotNitoceTV().setText("有效期:" +
                            DateUtil.getDateStr(account.getExpirydate(),
                                    StringPool.DATE_FORMAT_DATE));
                    titleLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (position == selectedOptionIndex){
                                return;
                            }
                            selectedOptionIndex = position;
                            reSelectOptions();
                        }
                    });
                    convertView = titleLayout;
                } else {
                    titleLayout = (JupiterRowStyleTitleLayout) convertView;
                }
                return convertView;
            }
        };
        paymodeOptionsLv.setAdapter(adapter);
    }

    private void reSelectOptions() {
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            JupiterRowStyleTitleLayout titleLayout = (JupiterRowStyleTitleLayout) paymodeOptionsLv.getChildAt(i);
            if (i == selectedOptionIndex){
                titleLayout.select(true);
            } else {
                titleLayout.select(false);
            }
        }
    }

    public boolean hasTicket() {
        if (paymodeInfo != null
                && paymodeInfo.getBizFinanceAccounts() != null
                && paymodeInfo.getBizFinanceAccounts().size() > 0
                && Payinfo.BizFinanceAccount.TYPE_TICKET.equals(paymodeInfo.getBizFinanceAccounts().get(0).getType())){
            return true;
        }
        return false;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        sutitleLayout.setOnClickListener(l);
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    @Override
    public boolean isSelected() {
        return sutitleLayout.isSelected();
    }

    @Override
    public void select(boolean isSelect) {
        if (isSelect){
            showDetail();
        } else {
            hideDetail();
        }
    }
}
