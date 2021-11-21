package com.example.android.notepad;


import android.app.Activity;
import android.view.View;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;


//public class NoteColor extends Activity implements View.OnClickListener {
public class NoteColor extends Activity{
    private boolean isFlag;

    /*private void showColor(){
        AlertDialog alertDialog=new AlertDialog.Builder(this).setTitle("请选择颜色").
                setIcon(R.mipmap.ic_launcher).setView(R.layout.color_layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

     */
/*
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orange:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#FF8C00"));
                    colorBack="#FF8C00";
                }else{
                    mText.setTextColor(Color.parseColor("#FF8C00"));
                    colorText="#FF8C00";
                }
                break;
            case R.id.white:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    colorBack="#FFFFFF";
                }else{
                    mText.setTextColor(Color.parseColor("#FFFFFF"));
                    colorText="#FFFFFF";
                }
                break;
            case R.id.blue:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#00FFFF"));
                    colorBack="#00FFFF";
                }else{
                    mText.setTextColor(Color.parseColor("#00FFFF"));
                    colorText="#00FFFF";
                }
                break;
            case R.id.gray:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#696969"));
                    colorBack="#696969";
                }else{
                    mText.setTextColor(Color.parseColor("#696969"));
                    colorText="#696969";
                }
                break;
            case R.id.purple:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#D81B60"));
                    colorBack="#D81B60";
                }else{
                    mText.setTextColor(Color.parseColor("#D81B60"));
                    colorText="#D81B60";
                }
                break;
            case R.id.green:
                if(isFlag){
                    mText.setBackgroundColor(Color.parseColor("#00FF7F"));
                    colorBack="#00FF7F";
                }else{
                    mText.setTextColor(Color.parseColor("#00FF7F"));
                    colorText="#00FF7F";
                }
                break;
        }
    }

 */


}


