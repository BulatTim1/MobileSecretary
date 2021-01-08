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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.peace.ttd.R;
import com.peace.ttd.ui.login.LoginFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn;
    private SharedPreferences sp;
    private SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        if (getActivity().getPreferences(Context.MODE_PRIVATE).getString("email", "").equals("")){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LoginFragment fragment = new LoginFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        TextView profile = root.findViewById(R.id.textView2);
        profile.setText(sp.getString("email", ""));
        btn = root.findViewById(R.id.button3);
        btn.setOnClickListener((v -> {
            edt = sp.edit();
            edt.putString("email", "");
            edt.apply();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LoginFragment fragment = new LoginFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }));
        return root;
    }
}