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
import com.sixst06.fragmentos.databinding.FragmentCategoriasBinding;
import com.sixst06.fragmentos.databinding.FragmentTopJuegosBinding;
import com.sixst06.fragmentos.gui.components.CategoriasAdapter;
import com.sixst06.fragmentos.gui.components.JuegosAdapter;
import com.sixst06.fragmentos.gui.components.NavigationIconClickListener;
import com.sixst06.fragmentos.model.Categoria;
import com.sixst06.fragmentos.model.Juego;

import java.util.ArrayList;
import java.util.List;

public class Categorias extends Fragment {

    private FragmentCategoriasBinding binding;
    private View view;
    private Context context;
    private List<Categoria> categorias = new ArrayList<>();

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
        MainActivity.GLOBALS.put("categoriasFragment", this);
    }

    private void configView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentCategoriasBinding.inflate(inflater,container,false);
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
                view.findViewById(R.id.gridCategorias),
                new AccelerateDecelerateInterpolator(),
                context.getDrawable(R.drawable.menu),
                context.getDrawable(R.drawable.menu_open)
        ));
    }

    private void configgUI() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            view.findViewById(R.id.gridCategorias).setBackground(getContext().getDrawable(R.drawable.product_grid_background_shape));
        }
    }

    private void configRecycler() {

        categorias.add(new Categoria(1, "accion", "Acción"));
        categorias.add(new Categoria(2, "aventura", "Aventuras"));
        categorias.add(new Categoria(3, "carreras", "Carreras"));
        categorias.add(new Categoria(4, "casual", "Casual"));
        categorias.add(new Categoria(5, "deportes", "Deportes"));
        categorias.add(new Categoria(6, "estrategia", "Estrategia"));
        categorias.add(new Categoria(7, "indie", "Indie"));
        categorias.add(new Categoria(8, "multijugador", "Multijugador"));
        categorias.add(new Categoria(9, "rol", "Rol"));
        categorias.add(new Categoria(10, "simuladores", "Simuladores"));

        binding.rclvCategorias.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        binding.rclvCategorias.setLayoutManager(layoutManager);
        binding.rclvCategorias.setAdapter(new CategoriasAdapter(categorias));

    }
}