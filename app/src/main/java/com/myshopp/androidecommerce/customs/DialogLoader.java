package com.myshopp.androidecommerce.customs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.support.v7.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;

import com.myshopp.androidecommerce.R;


/**
 * DialogLoader will be used to show and hide Dialog with ProgressBar
 **/

public class DialogLoader {

    private Context context;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialog;
    private LayoutInflater layoutInflater;


    public DialogLoader(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        initDialog();
    }

    
    private void initDialog() {
        dialog = new AlertDialog.Builder(context);
        View dialogView = layoutInflater.inflate(R.layout.layout_progress_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        ProgressBar dialog_progressBar = (ProgressBar) dialogView.findViewById(R.id.dialog_progressBar);
        dialog_progressBar.setIndeterminate(true);

        alertDialog = dialog.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void showProgressDialog() {
        alertDialog.show();
    }

    
    public void hideProgressDialog() {
        alertDialog.dismiss();
    }

    
}

