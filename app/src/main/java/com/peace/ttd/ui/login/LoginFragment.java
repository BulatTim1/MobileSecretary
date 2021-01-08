package com.peace.ttd.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.peace.ttd.DBHelper;
import android.content.Intent;
import android.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.widget.NestedScrollView;

import com.peace.ttd.R;
import com.peace.ttd.helpers.InputValidation;
import com.peace.ttd.ui.home.HomeFragment;
import com.peace.ttd.ui.registration.RegistrationFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class LoginFragment extends Fragment {
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private Button appCompatButtonLogin;
    private Button textViewLinkRegister;
    private InputValidation inputValidation;
    private DBHelper databaseHelper;
    private LoginViewModel loginViewModel;
    private Context appContext = this.getContext();
    private SharedPreferences sp;
    private SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);
//        if (!sp.getString("email", "").equals("")) {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            HomeFragment fragment = new HomeFragment();
//            ft.replace(R.id.nav_host_fragment, fragment);
//            ft.commit();
//        }
        nestedScrollView = (NestedScrollView) root.findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = (TextInputLayout) root.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) root.findViewById(R.id.textInputLayoutPassword);
        textInputEditTextEmail = (TextInputEditText) root.findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) root.findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin = root.findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = root.findViewById(R.id.textViewLinkRegister);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        edt = sp.edit();
        appCompatButtonLogin.setOnClickListener((v -> {
                verifyFromSQLite();}));
        textViewLinkRegister.setOnClickListener((v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RegistrationFragment fragment = new RegistrationFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }));
        databaseHelper = new DBHelper(this.getActivity());
        inputValidation = new InputValidation(this.getActivity());

        return root;
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            edt.putString("email", textInputEditTextEmail.getText().toString().trim());
            edt.apply();
            emptyInputEditText();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment fragment = new HomeFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }
        else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}