package com.doctor.imoocvoice.view.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctor.imoocvoice.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Doctor on 2021/1/11.
 */

public class MineFragment extends Fragment {

    private Context mContext;
    private TextView mTextView;

    public MineFragment() {

    }

    public static Fragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine_layout, null);
        mTextView = rootView.findViewById(R.id.textView);
        mTextView.setText("我的");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
