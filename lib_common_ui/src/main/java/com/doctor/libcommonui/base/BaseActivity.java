package com.doctor.libcommonui.base;

import android.os.Bundle;

import com.doctor.libcommonui.utils.StatusBarUtil;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by Doctor on 2021/1/12.
 */

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
    }
}
