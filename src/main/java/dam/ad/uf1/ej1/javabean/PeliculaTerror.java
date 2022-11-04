package dam.ad.uf1.ej1.javabean;

import jkutkut.ByteUtils;

/**
 * Clase con las condiciones para el almacenamiento de los datos de una película de terror en un fichero binario
 * aleatorio.
 *
 * @author Jorge Re -- Jkutkut
 */
public class PeliculaTerror {
    // Condiciones de longitud para los títulos.
    public static final int TITULO_LEN = 35;
    public static final int DIRECTOR_LEN = 35;
    public static final int SINOPSIS_LEN = 630;

    // Número total de atributos
    public static final int ATTRIBUTES = 5;

    // Orden de cada atributo
    public static final int TITULO = 0;
    public static final int ANIO = 1;
    public static final int DURACION = 2;
    public static final int DIRECTOR = 3;
    public static final int SINOPSIS = 4;

    /**
     * @return Tamaño de la clase en bytes en un fichero binarioAleatorio.
     */
    public static int sizeof() {
        return (TITULO_LEN + DIRECTOR_LEN + SINOPSIS_LEN) * ByteUtils.sizeof('c') +
                ByteUtils.sizeof(ANIO) + ByteUtils.sizeof(DURACION);
    }
}
