package com.yj.navigation.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.navigation.R;


public class DialogUtil {

	/**
	 * 功能：封装的dialog
	 * 
	 *  context
	 *  prompt
	 *  okButton
	 *  cancleButton
	 *  content
	 *  dialogUtilCallBack
	 */
	public static void alertDialogDelete(Context context, String prompt,
										 String okButton, String cancleButton, String content,
										 String contentComment, final DialogUtilCallBack dialogUtilCallBack,boolean boolCancel) {
		final Dialog dialog = new Dialog(context, R.style.myprocessstyle);
		dialog.setContentView(R.layout.wallet_dialog);
dialog.setCancelable(boolCancel);
		dialog.setCanceledOnTouchOutside(boolCancel);
		TextView alert_comment = (TextView) dialog
				.findViewById(R.id.alert_comment);
		alert_comment.setText(prompt);

		LinearLayout alert_content_line = (LinearLayout) dialog
				.findViewById(R.id.alert_content_line);

		alert_content_line.setVisibility(View.VISIBLE);

		TextView alert_content_comment1 = (TextView) dialog
				.findViewById(R.id.alert_content_comment1);

		TextView alert_content_comment2 = (TextView) dialog
				.findViewById(R.id.alert_content_comment2);
		alert_content_comment1.setText(content);

		alert_content_comment2.setText(contentComment);

		TextView alert_cancle_button = (TextView) dialog
				.findViewById(R.id.alert_cancle_button);
		alert_cancle_button.setText(cancleButton);
		alert_cancle_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialogUtilCallBack.cancleFuncBack();

			}
		});
		TextView alert_ok_button = (TextView) dialog
				.findViewById(R.id.alert_ok_button);
		alert_ok_button.setText(okButton);

		alert_ok_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialogUtilCallBack.okFuncBack();
			}
		});
		try {
			dialog.show();
		} catch (Exception e) {

		}

	}

	/**
	 * 功能：封装的dialog
	 *
	 *  context
	 *  prompt
	 *  okButton
	 *  cancleButton
	 *  content
	 *  dialogUtilCallBack
	 */
	public static void alertDialogStringParams(Context context, int prompt,
										 int okButton, int cancleButton, String content,
										 int contentComment, final DialogUtilCallBack dialogUtilCallBack) {
		final Dialog dialog = new Dialog(context, R.style.myprocessstyle);
		dialog.setContentView(R.layout.wallet_dialog);

		TextView alert_comment = (TextView) dialog
				.findViewById(R.id.alert_comment);
		alert_comment.setText(prompt);

		LinearLayout alert_content_line = (LinearLayout) dialog
				.findViewById(R.id.alert_content_line);

		alert_content_line.setVisibility(View.VISIBLE);

		TextView alert_content_comment1 = (TextView) dialog
				.findViewById(R.id.alert_content_comment1);

		TextView alert_content_comment2 = (TextView) dialog
				.findViewById(R.id.alert_content_comment2);
		alert_content_comment1.setText(content);

		alert_content_comment2.setText(contentComment);

		TextView alert_cancle_button = (TextView) dialog
				.findViewById(R.id.alert_cancle_button);
		alert_cancle_button.setText(cancleButton);
		alert_cancle_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialogUtilCallBack.cancleFuncBack();

			}
		});
		TextView alert_ok_button = (TextView) dialog
				.findViewById(R.id.alert_ok_button);
		alert_ok_button.setText(okButton);

		alert_ok_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialogUtilCallBack.okFuncBack();
			}
		});
		try {
			dialog.show();
		} catch (Exception e) {

		}

	}


	public interface DialogUtilCallBack {

		void okFuncBack();

		void cancleFuncBack();

	}

}
