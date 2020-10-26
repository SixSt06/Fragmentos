  package com.sixst06.fragmentos.gui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.databinding.FragmentLoginBinding;
import com.sixst06.fragmentos.gui.components.NavigationHost;

  public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        configGlobals();
        configView(inflater, container);
        configListener();
        return view;
    }

      private void configListener() {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LoginFragment", "onClick: xd");
                ((NavigationHost)MainActivity.GLOBALS.get("app")).navigateTo(new TopJuegos(), false);
            }
        });
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
      }

      private void configGlobals(){
        MainActivity.GLOBALS.put("loginFragment", this);
      }

      private void configView(LayoutInflater inflater, ViewGroup container){
        binding= FragmentLoginBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        context = container.getContext();
      }
  }