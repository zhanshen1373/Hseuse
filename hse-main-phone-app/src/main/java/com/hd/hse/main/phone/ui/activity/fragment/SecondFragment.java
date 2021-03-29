package com.hd.hse.main.phone.ui.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hd.hse.main.phone.R;

/**
 * Created by dubojian on 2017/7/19.
 */

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate1 = inflater.inflate(R.layout.dalian_main_secondview, null);

        return inflate1;
    }
}
