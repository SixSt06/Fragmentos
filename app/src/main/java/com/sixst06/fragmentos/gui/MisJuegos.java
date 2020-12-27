package com.sixst06.fragmentos.gui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.databinding.FragmentMisJuegosBinding;
import com.sixst06.fragmentos.gui.components.JuegosAdapter;
import com.sixst06.fragmentos.gui.components.MisJuegosAdapter;
import com.sixst06.fragmentos.gui.components.NavigationIconClickListener;
import com.sixst06.fragmentos.model.Juego;

import java.util.ArrayList;
import java.util.List;

public class MisJuegos extends Fragment {

    private FragmentMisJuegosBinding binding;
    private View view;
    private Context context;
    private List<Juego> juegos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        configGlobals();
        configView(inflater, container);
        configToolbar();
        configgUI();
        configRecycler();
        return view;
    }

    private void configGlobals() {
        MainActivity.GLOBALS.put("misJuegosFragment", this);
    }

    private void configView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentMisJuegosBinding.inflate(inflater,container,false);
        view = binding.getRoot();
        context = container.getContext();
    }

    private void configToolbar(){
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        if(activity != null){
            activity.setSupportActionBar(binding.appBar);
        }
        binding.appBar.setNavigationOnClickListener(new NavigationIconClickListener(
                context,
                view.findViewById(R.id.gridMisJuegos),
                new AccelerateDecelerateInterpolator(),
                context.getDrawable(R.drawable.menu),
                context.getDrawable(R.drawable.menu_open)
        ));
    }

    private void configgUI() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            view.findViewById(R.id.gridMisJuegos).setBackground(getContext().getDrawable(R.drawable.product_grid_background_shape));
        }
    }

    private void configRecycler() {

        juegos.add(new Juego("1", "modern", "Mordern Combat 5", 4, "Para Potato PC"));
        juegos.add(new Juego("2", "destiny", "Destiny 2", 5, "C H U L A D A"));
        juegos.add(new Juego("3", "doom", "Doom", 5, "El shooter padre"));
        juegos.add(new Juego("4", "metal", "Metal Slug", 5, "El mejor clasico de guerra"));
        juegos.add(new Juego("5", "ethernal", "Doom Ethernal", 4, "Mejor Shooter"));


        binding.rclvMisJuegos.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        binding.rclvMisJuegos.setLayoutManager(layoutManager);
        binding.rclvMisJuegos.setAdapter(new MisJuegosAdapter(juegos));

    }
}