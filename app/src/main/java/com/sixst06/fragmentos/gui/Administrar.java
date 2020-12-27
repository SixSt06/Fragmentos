package com.sixst06.fragmentos.gui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.databinding.FragmentAdministrarBinding;
import com.sixst06.fragmentos.gui.components.AdministrarAdapter;
import com.sixst06.fragmentos.gui.components.NavigationHost;
import com.sixst06.fragmentos.gui.components.NavigationIconClickListener;
import com.sixst06.fragmentos.model.Juego;

import java.util.ArrayList;
import java.util.List;

public class Administrar extends Fragment {

    private FragmentAdministrarBinding binding;
    private View view;
    private Context context;
    private List<Juego> juegos = new ArrayList<>();
    private static final String PATH_TOP="topJuegos";

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

        binding.fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost)getActivity()).navigateTo(new AddFragment(), true);
            }
        });

        return view;
    }

    private void configGlobals() {
        MainActivity.GLOBALS.put("administrarFragment", this);
    }

    private void configView(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentAdministrarBinding.inflate(inflater,container,false);
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
                view.findViewById(R.id.gridAdministrar),
                new AccelerateDecelerateInterpolator(),
                context.getDrawable(R.drawable.menu),
                context.getDrawable(R.drawable.menu_open)
        ));
    }

    private void configgUI() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            view.findViewById(R.id.gridAdministrar).setBackground(getContext().getDrawable(R.drawable.product_grid_background_shape));
        }
    }

    private void configRecycler() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_TOP);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Juego juego = snapshot.getValue(Juego.class);

                if (!juegos.contains(juego)) {
                    juegos.add(juego);
                }
                binding.rclvAdministrar.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Juego juego = snapshot.getValue(Juego.class);
                if (juego != null) {
                    Log.i("juego", "onChildChanged: " + juego.getIdJuego());
                }

                juegos.set(juegos.indexOf(juego), juego);
                binding.rclvAdministrar.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.rclvAdministrar.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,false);
        binding.rclvAdministrar.setLayoutManager(layoutManager);
        binding.rclvAdministrar.setAdapter(new AdministrarAdapter(juegos));
    }
}