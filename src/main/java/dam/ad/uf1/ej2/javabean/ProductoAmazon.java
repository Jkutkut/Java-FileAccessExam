package dam.ad.uf1.ej2.javabean;

import java.io.Serializable;

/**
 * Producto de Amazon.
 *
 * @author Jorge Re -- Jkutkut
 */
public class ProductoAmazon implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String descripcion;
    private final float puntuacion;
    private final int valoraciones;
    private final float precio;


    public ProductoAmazon(String descripcion, float puntuacion, int valoraciones, float precio) {
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.valoraciones = valoraciones;
        this.precio = precio;
    }

    // GETTERS

    public String getDescripcion() {
        return descripcion;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public int getValoraciones() {
        return valoraciones;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "ProductoAmazon{" +
                "descripcion='" + descripcion + '\'' +
                ", puntuacion=" + puntuacion +
                ", valoraciones=" + valoraciones +
                ", precio=" + precio +
                '}';
    }
}
