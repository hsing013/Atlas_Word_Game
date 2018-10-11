package com.example.harsh101.atlas;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Character.isLetter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrag extends Fragment {
    public Button login = null;
    public TextView banner = null;
    public TextView banner2 = null;
    public TextView usernameHint = null;
    public EditText userName = null;
    public EditText password = null;
    public View myView = null;

    public LoginFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (myView == null){
            myView = inflater.inflate(R.layout.fragment_login, container, false);
            login = myView.findViewById(R.id.loginButton);

            banner = myView.findViewById(R.id.banner);
            banner2 = myView.findViewById(R.id.banner2);
            userName = myView.findViewById(R.id.userInput);
            password = myView.findViewById(R.id.passInput);
            usernameHint = myView.findViewById(R.id.hint);

            usernameHint.setVisibility(View.INVISIBLE); //initially the username hint is invisible because it belongs to the signup screen

            usernameHint.setText("Username must start with a letter and must be at least 8 letters long.\nSymbols and whitespace are not allowed.");
        }

        return myView;
    }

    public boolean checkInput(String name, String pass){
//        if (name == "")
//        {
//            return false;
//        }
//
//        else if (!isLetter(name.charAt(0)))
//        {
//            return false;
//        }
//
//        else if (name.contains(" "))
//        {
//            return false;
//        }
//
//        else if (name.length() < 6)
//        {
//            return false;
//        }
//
//        else if (!StringUtils.isAlphanumeric(name))
//        {
//            for (int i = 0; i < name.length(); ++i)
//            {
//                if (name.charAt(i) == '_') {
//                    continue;
//                }
//
//                else
//                {
//                    return false;
//                }
//            }
//        }
//
//
//        if (pass == "")
//        {
//            return false;
//        }
//
//        else if (pass.contains(" "))
//        {
//            return false;
//        }

        return true;
    }

    public ArrayList<String> readInput(){
        String name = userName.getText().toString();
        String pass = password.getText().toString();
        //userName.setText("");
        //password.setText("");
//        if (!checkInput(name, pass)){
//            return null;
//        }
        ArrayList<String> info = new ArrayList<>();
        info.add(name);
        info.add(pass);
        return info;
    }

    public void disableButton(boolean flag){
        if (flag){
            login.setEnabled(false);
            banner2.setEnabled(false);
        }
        else{
            login.setEnabled(true);
            banner2.setEnabled(true);
        }
    }

    public void flipToSignUp(boolean flag){
        if (flag){
            login.setText("Signup");
            banner.setText("Signup here!");
            banner2.setText("Back to the Login?");
            userName.setText("");
            password.setText("");
            usernameHint.setVisibility(View.VISIBLE);
        }
        else{
            login.setText("Login");
            banner.setText("Login here!");
            banner2.setText("Looking to signup?");
            userName.setText("");
            password.setText("");
            usernameHint.setVisibility(View.INVISIBLE);
        }
    }

}
