package com.peace.ttd.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.peace.ttd.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn;
    private SharedPreferences sp;
    private SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
//        btn = root.findViewById(R.id.button);
//        btn.setOnClickListener((v -> {
//            Snackbar.make(v, sp.getString("email", ""), Snackbar.LENGTH_LONG).show();
//        }));
        return root;
    }
}