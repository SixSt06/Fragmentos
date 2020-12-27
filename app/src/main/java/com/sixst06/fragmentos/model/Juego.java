package com.sixst06.fragmentos.model;

import java.util.Objects;

public class Juego {
    private String idJuego;
    private String imagen;
    private String titulo;
    private float clasificacion;
    private String descripcion;

    public Juego() {
    }

    public Juego(String idJuego, String imagen, String titulo, float clasificacion, String descripcion) {
        this.idJuego = idJuego;
        this.imagen = imagen;
        this.titulo = titulo;
        this.clasificacion = clasificacion;
        this.descripcion = descripcion;
    }

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
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

    public float getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(float clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return idJuego.equals(juego.idJuego);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJuego);
    }
}
