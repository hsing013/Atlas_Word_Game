package com.example.harsh101.atlas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    enum State {initial_Login, initial_Signup, loginAttempt, signupAttempt, loggedIn}

    public State state = State.initial_Login;

    public Button login = null;
    public TextView banner = null;
    public TextView banner2 = null;
    public EditText userName = null;
    public EditText password = null;
    public TextView usernameHint = null;
    public BottomNavigationView navigation = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment frag){
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.mainFrame, frag);
        fragTrans.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.INVISIBLE); //initially the bottom tab bar is invisible

        login = findViewById(R.id.loginButton);
        banner = findViewById(R.id.banner);
        banner2 = findViewById(R.id.signUptext);
        userName = findViewById(R.id.userNameInput);
        password = findViewById(R.id.passwordInput);
        usernameHint = findViewById(R.id.userNameHint);

        usernameHint.setVisibility(View.INVISIBLE); //initially the username hint is invisible because it belongs to the signup screen

        usernameHint.setText("Username must start with a letter and must be atleast 8 letters long.\n Symbols and whitespace not allowed.");

    }

    /*If the user hits the looking for signup
        text then it switches them to signup screen
        but if the click it again, it will take them back to the
        login screen
     */

    public void signUpClick(View v){
        if (state == State.initial_Login){
            login.setText("Signup");
            banner.setText("Signup here!");
            banner2.setText("Back to the Login?");
            userName.setText("");
            password.setText("");
            state = State.initial_Signup;
            usernameHint.setVisibility(View.VISIBLE);
        }
        else{
            login.setText("Login");
            banner.setText("Login here!");
            banner2.setText("Looking to signup?");
            userName.setText("");
            password.setText("");
            state = State.initial_Login;
            usernameHint.setVisibility(View.INVISIBLE);
        }
    }

    public void loginButtonClick(View v){

    }

}
