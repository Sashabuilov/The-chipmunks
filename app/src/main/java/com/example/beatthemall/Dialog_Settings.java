package com.example.beatthemall;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Dialog_Settings extends DialogFragment implements OnClickListener {
    private CheckBox cb;

    public SharedPreferences mSettings;
    public static final String APP_PREFERENCES_CB = "CB_Musik";

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getDialog().setTitle("Title!");

        View v = inflater.inflate(R.layout.dialog_settings, null);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar);

        cb = v.findViewById(R.id.cb_background_music);
        v.findViewById(R.id.ok_settings).setOnClickListener(this);

        cb.setChecked(AppSession.getInstance().getIsPlayMusic());

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppSession.getInstance().setIsPlayMusic(isChecked);
            }
        });
        return v;
    }

    /*@Override
    public void onResume()
    {
        super.onResume();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar);
    }
*/


    public void onClick(View v) {


        dismiss();
    }
}

