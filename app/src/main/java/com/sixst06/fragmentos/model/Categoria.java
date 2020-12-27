package com.sixst06.fragmentos.model;

public class Categoria {
    private int idCategoria;
    private String imagen;
    private String titulo;

    public Categoria() {
    }

    public Categoria(int idCategoria, String imagen, String titulo) {
        this.idCategoria = idCategoria;
        this.imagen = imagen;
        this.titulo = titulo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
