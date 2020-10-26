package com.sixst06.fragmentos.gui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.model.Juego;

import java.util.List;

public class JuegosAdapter extends RecyclerView.Adapter<JuegosAdapter.ViewHolder> {

    private List<Juego> juegos;
    private Context context;

    public JuegosAdapter(List<Juego> juegos) {
        this.juegos = juegos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_juego, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Juego juego = juegos.get(position);
        String imgUri = "@drawable/" + juego.getImagen();
        int imgResource = context.getResources().getIdentifier(imgUri, null, context.getPackageName());
        holder.imgJuegos.setImageResource(imgResource);
        holder.txtTitulo.setText(juego.getTitulo());
        holder.rbClasificacion.setRating(juego.getClasificacion());
        holder.txtDescripcion.setText(juego.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return juegos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private AppCompatImageView imgJuegos;
        private TextView txtTitulo;
        private AppCompatRatingBar rbClasificacion;
        private TextView txtDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgJuegos = itemView.findViewById(R.id.imgJuego);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            rbClasificacion = itemView.findViewById(R.id.rbClasificacion);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            this.view = itemView;
        }
    }
}
