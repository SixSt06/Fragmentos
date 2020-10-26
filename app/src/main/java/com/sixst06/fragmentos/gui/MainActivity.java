package com.sixst06.fragmentos.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.core.FragmentosApplication;
import com.sixst06.fragmentos.gui.components.NavigationHost;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    public static HashMap<String, Object> GLOBALS = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        configContext();
        configGlobals();
        configFragmentManager(savedInstanceState);
    }

    private void configContext(){
        FragmentosApplication.setAppContext(getApplicationContext());
    }

    private void configGlobals(){
        GLOBALS.put("app", this);
    }

    private void configFragmentManager(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentPanel, new LoginFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left).replace(R.id.contentPanel, fragment);
        if(addToBackStack){
            transaction.addToBackStack(null);
        transaction.commit();
        }
    }
}