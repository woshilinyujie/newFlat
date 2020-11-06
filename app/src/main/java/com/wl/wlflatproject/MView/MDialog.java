package com.wl.wlflatproject.MView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wl.wlflatproject.R;

public class MDialog extends Dialog {

    private TextView textView;

    public MDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected MDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void init(Context context){
        View view = View.inflate(context, R.layout.mdialog, null);
        textView = view.findViewById(R.id.text);
        setContentView(view);
    }

    public void setMsg(String s){
        textView.setText(s);
    }
}
