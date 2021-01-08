package com.peace.ttd.ui.registration;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.peace.ttd.R;
import com.peace.ttd.helpers.InputValidation;
import com.peace.ttd.model.User;
import com.peace.ttd.DBHelper;
import com.peace.ttd.ui.home.HomeFragment;
import com.peace.ttd.ui.login.LoginFragment;
import com.peace.ttd.ui.login.LoginViewModel;

public class RegistrationFragment extends Fragment {

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private Button appCompatButtonRegister;
    private Button appCompatTextViewLoginLink;
    private InputValidation inputValidation;
    private DBHelper databaseHelper;
    private User user;
    private RegistrationViewModel registrationViewModel;
    private SharedPreferences sp;
    private SharedPreferences.Editor edt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrationViewModel =
                new ViewModelProvider(this).get(RegistrationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        nestedScrollView = (NestedScrollView) root.findViewById(R.id.nestedScrollView);
        textInputLayoutName = (TextInputLayout) root.findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) root.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) root.findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) root.findViewById(R.id.textInputLayoutConfirmPassword);
        textInputEditTextName = (TextInputEditText) root.findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) root.findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) root.findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) root.findViewById(R.id.textInputEditTextConfirmPassword);
        appCompatButtonRegister = root.findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = root.findViewById(R.id.appCompatTextViewLoginLink);
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        edt = sp.edit();
        appCompatButtonRegister.setOnClickListener((v -> {
            postDataToSQLite();
        }));
        appCompatTextViewLoginLink.setOnClickListener((v -> {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            LoginFragment fragment = new LoginFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
        }));
        inputValidation = new InputValidation(this.getActivity());
        databaseHelper = new DBHelper(this.getActivity());
        user = new User();
        return root;
    }
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            edt.putString("username", textInputEditTextName.getText().toString().trim());
            edt.putString("email", textInputEditTextEmail.getText().toString().trim());
            edt.apply();
            emptyInputEditText();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment fragment = new HomeFragment();
            ft.replace(R.id.nav_host_fragment, fragment);
            ft.commit();
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}