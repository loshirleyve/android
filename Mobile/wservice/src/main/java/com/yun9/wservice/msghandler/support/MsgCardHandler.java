package com.yun9.wservice.msghandler.support;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Msg;
import com.yun9.wservice.model.MsgCard;
import com.yun9.wservice.model.MsgCardPraise;
import com.yun9.wservice.msghandler.MsgHandler;
import com.yun9.wservice.view.dynamic.NewDynamicActivity;
import com.yun9.wservice.view.dynamic.NewDynamicCommand;
import com.yun9.wservice.view.msgcard.MsgCardDetailActivity;
import com.yun9.wservice.view.msgcard.MsgCardDetailCommand;
import com.yun9.wservice.view.msgcard.MsgCardListActivity;
import com.yun9.wservice.view.msgcard.widget.MsgCardWidget;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

import java.util.List;

/**
 * Created by huangbinglong on 8/7/15.
 */
public class MsgCardHandler implements MsgHandler{

    private ResourceFactory resourceFactory;

    private SessionManager sessionManager;

    @Override
    public String getType() {
        return "msgcard";
    }

    @Override
    public View getView(CustomCallbackActivity activity, Msg msg) {
        MsgCardWidget msgCardWidget = createView(activity);
        loadData(activity,msgCardWidget,msg);
        return msgCardWidget;
    }

    @Override
    public void loadData(final CustomCallbackActivity activity,View convertView, Msg msg) {
        final MsgCardWidget msgCardWidget = (MsgCardWidget) convertView;
        final MsgCard msgCard = msg.getMsgCard();
        msgCardWidget.getPraiseRL().setTag(msg.getMsgCard());
        msgCardWidget.setTag(msgCard);
        msgCardWidget.buildWithData(msgCard);
        msgCardWidget.getPraiseRL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPraiseLikeByMsgCardId(activity, msgCard, msgCardWidget);
            }
        });
        msgCardWidget.getFwRL().setTag(msgCard);
        msgCardWidget.getCommentRL().setTag(msgCard);
        msgCardWidget.getFwRL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forwardMsgCardId = msgCard.getId();
                forward(activity,forwardMsgCardId);
            }
        });
        msgCardWidget.getCommentRL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgCard msgCard = (MsgCard) v.getTag();
                if (AssertValue.isNotNull(msgCard)) {
                    MsgCardDetailActivity.start(activity, new MsgCardDetailCommand().setMsgCardId(msgCard.getId()).setScrollComment(true));
                }
            }
        });
    }

    private void forward(final CustomCallbackActivity activity,final String msgCardId) {
        activity.addActivityCallback(OrgCompositeCommand.REQUEST_CODE, new CustomCallbackActivity.IActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                if (resultCode != JupiterCommand.RESULT_CODE_OK){
                    return;
                }
                List<User> users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
                List<Org> orgs = (List<Org>) data.getSerializableExtra(OrgCompositeCommand.PARAM_ORG);
                if ((AssertValue.isNotNullAndNotEmpty(users) || AssertValue.isNotNullAndNotEmpty(orgs))
                        && AssertValue.isNotNullAndNotEmpty(msgCardId)) {
                    NewDynamicCommand newDynamicCommand = new NewDynamicCommand().setMsgCardId(msgCardId)
                            .setSelectUsers(users).setSelectOrgs(orgs)
                            .setType(NewDynamicCommand.MSG_FORWARD);
                    NewDynamicActivity.start(activity, newDynamicCommand);
                }
            }
        });
        OrgCompositeActivity.start(activity,
                new OrgCompositeCommand().setEdit(true)
                        .setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK)
                        .setSingleSelect(true).setOnlyUsers(true));

    }

    private MsgCardWidget createView(final Activity activity) {
        final MsgCardWidget msgCardWidget = new MsgCardWidget(activity);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 20, 20, 20);
        msgCardWidget.getMainRl().setLayoutParams(params);

        return msgCardWidget;
    }

    private void cardPraiseLikeByMsgCardId(final Activity activity,
                                        final MsgCard msgCard,
                                           final MsgCardWidget msgCardWidget) {
        sessionManager = JupiterApplication.getBeanManager().get(SessionManager.class);
        resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
        String msgcardId = msgCard.getId();
        if (AssertValue.isNotNull(sessionManager.getUser())) {
            Resource resource = resourceFactory.create("AddPraiseLikeByMsgCardId");
            resource.param("userid", sessionManager.getUser().getId());
            resource.param("msgcardid", msgcardId);
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    MsgCardPraise msgCardPraise = (MsgCardPraise) response.getPayload();
                    if (AssertValue.isNotNull(msgCardPraise)) {
                        int praiseNum = Integer.parseInt(msgCardWidget.getPraiseNumTV().getText().toString());
                        if (msgCardPraise.getPraise() == 0) {
                            msgCardWidget.getPraiseIV().setImageResource(R.drawable.some_praise1);
                            if (praiseNum != 0) {
                                msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum - 1));
                            } else {
                                msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum + 1));
                            }
                        } else {
                            msgCardWidget.getPraiseIV().setImageResource(R.drawable.some_praise);
                            msgCardWidget.getPraiseNumTV().setText(String.valueOf(praiseNum + 1));
                        }
                    }
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(activity, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                }
            });
        }
    }
}
