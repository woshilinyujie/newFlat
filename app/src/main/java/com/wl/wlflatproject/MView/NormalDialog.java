package com.wl.wlflatproject.MView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.wl.wlflatproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NormalDialog extends Dialog {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.cancel_tv)
    TextView cancelTv;
    @BindView(R.id.split_view)
    View splitView;
    @BindView(R.id.confirm_tv)
    TextView confirmTv;

    public NormalDialog(@NonNull Context context) {
        super(context);
        initLayout(context);
    }

    public NormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initLayout(context);
    }




    protected void initLayout(Context context) {
        View content = View.inflate(context,R.layout.dialog_common_normal, null);
        ButterKnife.bind(this, content);
        setContentView(content);
    }

    /**
     * 标题
     *
     * @param title
     */
    public void setTitleText(String title) {
        if (title != null) {
            titleTv.setText(title);
        }
    }

    /**
     * 标题
     *
     * @param color
     */
    public void setTitleColor(String color) {
        if (color != null) {
            titleTv.setTextColor(Color.parseColor(color));
        }
    }

    /**
     * 内容
     *
     * @param content
     */
    public void setContentText(String content) {
        if (content != null) {
            contentTv.setText(content);
        }
    }

    /**
     * 内容
     *
     * @param color
     */
    public void setContentColor(String color) {
        if (color != null) {
            contentTv.setTextColor(Color.parseColor(color));
        }
    }

    /**
     * 取消
     *
     * @param cancel
     */
    public void setCancelText(String cancel) {
        if (cancel != null) {
            cancelTv.setText(cancel);
        }
    }

    /**
     * 取消
     *
     * @param color
     */
    public void setCancelColor(String color) {
        if (color != null) {
            cancelTv.setTextColor(Color.parseColor(color));
        }
    }


    /**
     * 确定
     *
     * @param confirm
     */
    public void setConfirmText(String confirm) {
        if (confirm != null) {
            confirmTv.setText(confirm);
        }
    }

    /**
     * 确定
     *
     * @param color
     */
    public void setConfirmColor(String color) {
        if (color != null) {
            confirmTv.setTextColor(Color.parseColor(color));
        }
    }

    /**
     * 设置返回键无效
     */
    public void setcancelBack() {
        setCancelable(false);
        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 显示单个按钮
     *
     * @param isConfirm
     */
    public void showSingleButton(boolean isConfirm) {
        splitView.setVisibility(View.GONE);
        if (isConfirm) {
            confirmTv.setVisibility(View.VISIBLE);
            cancelTv.setVisibility(View.GONE);
        } else {
            confirmTv.setVisibility(View.GONE);
            cancelTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 默认按钮显示
     */
    public void showDefaultButton() {
        splitView.setVisibility(View.VISIBLE);
        confirmTv.setVisibility(View.VISIBLE);
        cancelTv.setVisibility(View.VISIBLE);
    }

    /**
     * 取消
     */
    @OnClick(R.id.cancel_tv)
    public void onCancelTvClicked() {
        if (mOnDialogCancelListener != null) {
            mOnDialogCancelListener.onClickCancel();
        } else {
            dismiss();
        }
    }





    /**
     * Interface 取消
     */
    public interface OnDialogCancelListener {

        /**
         * 取消
         */
        void onClickCancel();
    }

    private OnDialogCancelListener mOnDialogCancelListener;

    public void setOnDialogCancelListener(OnDialogCancelListener onDialogCancelListener) {
        mOnDialogCancelListener = onDialogCancelListener;
    }

    public TextView getConfirmTv(){
        return confirmTv;
    }
}
