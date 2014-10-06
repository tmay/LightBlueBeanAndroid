package com.terrymay.lightbluebean.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.terrymay.lightbluebean.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import colorpicker.ColorPicker;
import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanListener;
import nl.littlerobots.bean.message.Callback;
import nl.littlerobots.bean.message.DeviceInfo;


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

    @ViewById(R.id.txt_firmware_version)
    TextView txtFirmwareVersion;

    @ViewById(R.id.txt_hardware_version)
    TextView txtHardwareVersion;

    @ViewById(R.id.txt_software_version)
    TextView txtSoftwareVersion;

    @ViewById(R.id.btn_advertising)
    ToggleButton btnAdvertising;

    //https://github.com/chiralcode/Android-Color-Picker/
    @ViewById(R.id.widget_color_picker)
    ColorPicker colorPicker;

    private Bean bean;

    @AfterViews
    void onAfterViews() {
        this.bean = getArguments().getParcelable(BEAN_EXTRA);

        if (bean != null && !bean.isConnected()) {
            bean.connect(getActivity(), this);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bean.disconnect();
    }

    @UiThread
    void updateTempDisplay(Integer temp) {
        txtTemp.setText(temp+"°");
    }

    @UiThread
    void updateDeviceInfo(DeviceInfo result) {
        txtFirmwareVersion.setText(result.firmwareVersion());
        txtHardwareVersion.setText(result.hardwareVersion());
        txtSoftwareVersion.setText(result.softwareVersion());
    }

    @Override
    public void onConnected() {
        bean.readTemperature(new Callback<Integer>() {
            @Override
            public void onResult(Integer integer) {
                updateTempDisplay(integer);
            }
        });

        bean.readDeviceInfo(new Callback<DeviceInfo>() {
            @Override
            public void onResult(DeviceInfo result) {
                updateDeviceInfo(result);
            }
        });


        colorPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int color = colorPicker.getColor();
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);

                bean.setLed(red, green, blue);
                return false;
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
