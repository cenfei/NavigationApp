package com.yj.navigation.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yj.navigation.R;
import com.yj.navigation.gridpasswordview.GridPasswordView;


/**
 * Created by foxcen on 15/8/28.
 */
public class FoxShuashuaPayPasswordInterface {
    public void startProgressBar(Context context, String payContent, final CallBackPasswordDialog callBackPasswordDialog) {
// 加载popuwindow 菊花
        dialog = new Dialog(context, R.style.mystyle_password);
//     View view=      (View)dialog.findViewById(R.layout.shuashua_popupwindow_alert);
        dialog.setContentView(R.layout.password_popup_alert);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        try {
            dialog.show();
        } catch (Exception e) {

        }
        // wallet_bind_line = (LinearLayout) dialog
        // .findViewById(R.id.wallet_bind_line);

//        wallet_bind_image = (ImageView) dialog
//                .findViewById(R.id.wallet_bind_image);
        TextView pay_comment3 = (TextView) dialog
                .findViewById(R.id.pay_comment3);
        if(payContent!=null&&!payContent.equals("")) {
            pay_comment3.setText(payContent);
        }
//        pay_comment2.setText(payContent);
//        LinearLayout title_left_botton = (LinearLayout) dialog
//                .findViewById(R.id.title_left_botton);
//        title_left_botton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callBackPasswordDialog.handleDialogResultCancle();
//
//                dialog.dismiss();
//            }
//        });
        final GridPasswordView gpv_normal_paypassword = (GridPasswordView) dialog
                .findViewById(R.id.gpv_normal_paypassword);

//        gpv_normal_paypassword.forceInputViewGetFocus();

//        gpv_normal_paypassword.setFocusable(true);
//        gpv_normal_paypassword.setFocusableInTouchMode(true);
//        gpv_normal_paypassword.requestFocus();
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//                           public void run() {
//
//                               InputMethodManager inputManager =
//                                       (InputMethodManager) gpv_normal_paypassword.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                               inputManager.showSoftInput(gpv_normal_paypassword, 0);
//                           }
//
//                       },
//                200);


        gpv_normal_paypassword.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                callBackPasswordDialog.handleDialogResultOk(gpv_normal_paypassword, psw,  dialog);
//                dialog.dismiss();
            }
        });
//        gpv_normal_paypassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callBackPasswordDialog.handleDialogResultOk(gpv_normal_paypassword);
//                dialog.dismiss();
//            }
//        });

//        initDots(dialog);

//        startWaiting();

    }


    public interface CallBackPasswordDialog {
        void handleDialogResultOk(GridPasswordView gpv_normal_paypassword, String pwd,Dialog dialog);

//        void handleDialogResultCancle();

    }

    public void stopProgressBar() {
//        stopWaiting();
        dialog.dismiss();
    }

    private Dialog dialog;
//    private RotateAnimation rotateAnimation;
//    private boolean isRotating = false;
//    private ImageView wallet_bind_image;
//
//    private void startWaiting() {
//
//
//        writehandler.post(runnableRecharge);
//
//
//    }
//
//    final Handler writehandler = new Handler();
//
//    Runnable runnableRecharge = new Runnable() {
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            //要做的事情
//            int postion = 0;
//            if (currentIndex == 0) {
//                postion = 2;
//            } else {
//                postion = currentIndex - 1;
//            }
//            setCurDot(postion);
//            currentIndex++;
//            if (currentIndex > 2) {
//                currentIndex = 0;
//            }
//            writehandler.postDelayed(this, 500);
//
//
//        }
//    };
//
//
//    private void stopWaiting() {
//        if (writehandler != null) {
//            writehandler.removeCallbacks(runnableRecharge);
//        }
//
//    }


//    public interface CallBackDialog {
//        void handleDialogResultOk(DialogInterface dialog, int which);
//        void handleDialogResultCancle(DialogInterface dialog, int which);
//
//    }



}
