package com.example.e_tecklaptop.testproject.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.e_tecklaptop.testproject.R;

public class CustomDialog {

    Dialog dialog;
    Context mcontext;

    public CustomDialog(Context context){
        mcontext = context;
    }

    public void ShowDialog(){
        dialog = new Dialog(mcontext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        if(!(dialog.isShowing())) {
            dialog.show();
        }

    }

    public void HideDialog(){

        if(dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
        }
    }


}
