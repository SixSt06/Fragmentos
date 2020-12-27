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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.gui.Administrar;
import com.sixst06.fragmentos.model.Juego;

import java.util.List;

public class AdministrarAdapter extends RecyclerView.Adapter<AdministrarAdapter.ViewHolder>{   private List<Juego> administraciones;
    private Context context;

    public AdministrarAdapter(List<Juego> administraciones) {
        this.administraciones = administraciones;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mis_juegos, parent,false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Juego administracion = administraciones.get(position);
        String imgUri = administracion.getImagen();
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

                .centerCrop();

        Glide.with(context)
                .load(imgUri)
                .placeholder(R.drawable.ic_good)
                .error(R.drawable.ic_error)
                .apply(options)
                .into(holder.imgAdministra);

        holder.txtTitulo.setText(administracion.getTitulo());
        holder.rbClasificacion.setRating(administracion.getClasificacion());
        holder.txtDescripcion.setText(administracion.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return administraciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private AppCompatImageView imgAdministra;
        private TextView txtTitulo;
        private AppCompatRatingBar rbClasificacion;
        private TextView txtDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAdministra = itemView.findViewById(R.id.imgJuego);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            rbClasificacion = itemView.findViewById(R.id.rbClasificacion);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            this.view = itemView;
        }
    }
}
