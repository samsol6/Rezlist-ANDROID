package com.example.e_tecklaptop.testproject.utils;


import android.app.Dialog;
import android.content.Context;

import com.example.e_tecklaptop.testproject.R;

public class CustomDialog {

    Dialog dialog;
    Context mcontext;

    public CustomDialog(Context context){
        mcontext = context;
    }

    public void ShowDialog(){
        dialog = new Dialog(mcontext);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void HideDialog(){
        dialog.dismiss();
    }


}
