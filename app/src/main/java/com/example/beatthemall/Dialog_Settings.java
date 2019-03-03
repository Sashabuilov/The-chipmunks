package com.example.beatthemall;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class Dialog_Settings extends DialogFragment implements OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_settings, null);
        v.findViewById(R.id.ok_settings).setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {

        dismiss();
    }

}

