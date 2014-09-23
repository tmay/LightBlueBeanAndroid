package com.terrymay.lightbluebean.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.terrymay.lightbluebean.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanListener;
import nl.littlerobots.bean.message.Callback;


/**
 * Created by Terry on 9/21/14.
 */
@EFragment(R.layout.fragment_bean_device)
public class BeanDeviceDisplay extends Fragment implements BeanListener {
    public final static String BEAN_EXTRA = "bean";

    public static BeanDeviceDisplay newInstance(Bean bean) {
        Bundle args = new Bundle();
        args.putParcelable(BEAN_EXTRA, bean);

        BeanDeviceDisplay fragment = new BeanDeviceDisplay_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.txt_temp)
    TextView txtTemp;

    private Bean bean;

    @AfterViews
    void onAfterViews() {
        this.bean = getArguments().getParcelable(BEAN_EXTRA);

        if (bean != null && !bean.isConnected()) {
            bean.connect(getActivity(), this);

        }

    }

    @UiThread
    void updateTempDisplay(Integer temp) {
        txtTemp.setText(temp+"°");
    }

    @Override
    public void onConnected() {
        bean.readTemperature(new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                updateTempDisplay(integer);
            }
        });
    }

    @Override
    public void onConnectionFailed() {

    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public void onSerialMessageReceived(byte[] bytes) {

    }

    @Override
    public void onScratchValueChanged(int i, byte[] bytes) {

    }
}
