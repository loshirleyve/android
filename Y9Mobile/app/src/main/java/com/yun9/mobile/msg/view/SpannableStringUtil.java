package com.yun9.mobile.msg.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.HtmlUtil;
import com.yun9.mobile.framework.util.HtmlUtil.UrlEle;
import com.yun9.mobile.msg.activity.ShowUrlActivity;

public class SpannableStringUtil {

	public SpannableString fromMsgCardContent(String content, final TextView tvContent) {
		if (!AssertValue.isNotNullAndNotEmpty(content)) {
			return new SpannableString("");
		}
		List<NoLineClickSpan> spans = new ArrayList<SpannableStringUtil.NoLineClickSpan>();
		List<UrlEle> urls = HtmlUtil.getUrls(content);
		int offset = 0;
		int start = 0;
		for (UrlEle url : urls) {
			content = content.replaceFirst(url.getUrl(), "#网页链接");
			start = url.getStart() - offset;
			spans.add(new NoLineClickSpan(url.getUrl(), start, start + 5));
			offset += url.getUrl().length() -5;
		}
		SpannableString sp = new SpannableString(content);
		for (NoLineClickSpan span : spans) {
			DynamicDrawableSpan drawableSpan =
					 new DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BASELINE) {
					    @Override
					    public Drawable getDrawable() {
					        Drawable d = tvContent.getResources().getDrawable(R.drawable.link_easyicon_net_24);
					        d.setBounds(0, 0, 30, 30);
					        return d;
					    }
					};
			sp.setSpan(drawableSpan, span.getStart(),span.getStart() + 1,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			sp.setSpan(span, span.getStart()+1, span.getEnd(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		tvContent.setMovementMethod(LinkMovementMethod.getInstance());
		return sp;
	}

	private class NoLineClickSpan extends ClickableSpan {
		private String url;
		private int start;
		private int end;

		public NoLineClickSpan(String url, int start, int end) {
			super();
			this.url = url;
			this.start = start;
			this.end = end;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(ds.linkColor);
			ds.setUnderlineText(false);
		}

		@Override
		public void onClick(View widget) {
			Intent intent = new Intent(widget.getContext(), ShowUrlActivity.class);
			intent.putExtra("url",url);
			widget.getContext().startActivity(intent);
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}
	}
}
