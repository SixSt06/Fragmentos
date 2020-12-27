package com.sixst06.fragmentos.gui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.sixst06.fragmentos.R;
import com.sixst06.fragmentos.model.Categoria;

import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private List<Categoria> categorias;
    private Context context;

    public CategoriasAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public CategoriasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_categorias, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasAdapter.ViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        String imgUri = "@drawable/" + categoria.getImagen();
        int imgResource = context.getResources().getIdentifier(imgUri, null, context.getPackageName());
        holder.imgCategoria.setImageResource(imgResource);
        holder.txtTitulo.setText(categoria.getTitulo());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView imgCategoria;
        private View view;
        private TextView txtTitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            this.view = itemView;
        }
    }
}
