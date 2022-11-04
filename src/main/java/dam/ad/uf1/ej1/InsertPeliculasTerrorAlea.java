package dam.ad.uf1.ej1;

import dam.ad.uf1.ej1.javabean.PeliculaTerror;
import jkutkut.ByteUtils;
import jkutkut.Exception.InvalidDataException;

import java.io.*;

/**
 * Programa que almacena peliculas de terror.
 * Toma un archivo de input en texto plano (.txt) y lo convierte en un archivo binario aleatorio (.dat)
 *
 * @author Jorge Re -- Jkutkut
 */
public class InsertPeliculasTerrorAlea {
    private static final String INPUT_FILE = "res/pelis_terror.txt";
    private static final String OUTPUT_FILE = "res/pelis_terror.dat";

    private static final String INPUT_DELIMETER = "-";

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
            RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
            f.seek(f.length()); // append

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(INPUT_DELIMETER);
                writePeliculaTerror(f, getLastId(f) + 1, data);
            }

            System.out.println("Peliculas de terror guardadas en " + OUTPUT_FILE);
            f.close();
            br.close();
        } catch (InvalidDataException e) {
            System.err.println(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println("El archivo no es válido");
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // testRead();
    }

    private static int getLastId(RandomAccessFile f) throws IOException {
        final int INT_SIZE = ByteUtils.sizeof((int) 1);
        final int OBJ_SIZE = PeliculaTerror.sizeof() + INT_SIZE;
        if (f.getFilePointer() < OBJ_SIZE)
            return 0;
        long lastIdPos = f.getFilePointer() - OBJ_SIZE;
        f.seek(lastIdPos);
        int id = f.readInt();
        f.seek(f.getFilePointer() + OBJ_SIZE - INT_SIZE); // Vuelve
        return id;
    }

    /**
     * Escribe una película de terror en el archivo dado.
     * @param f Archivo a usar.
     * @param id Id de la película en cuestión.
     * @param data Array de strings con los datos de la película siguiend el orden del Javabean.
     * @throws IOException
     * @throws InvalidDataException
     */
    private static void writePeliculaTerror(RandomAccessFile f, int id, String[] data) throws IOException, InvalidDataException {
        if (data.length != PeliculaTerror.ATTRIBUTES)
            throw new InvalidDataException("El archivo tiene una entrada inválida");
        System.out.println("Guardando " + data[PeliculaTerror.TITULO]);
        f.writeInt(id);
        writeString(f, data[PeliculaTerror.TITULO], PeliculaTerror.TITULO_LEN);
        writeInt(f, data[PeliculaTerror.ANIO]);
        writeInt(f, data[PeliculaTerror.DURACION]);
        writeString(f, data[PeliculaTerror.DIRECTOR], PeliculaTerror.DIRECTOR_LEN);
        writeString(f, data[PeliculaTerror.SINOPSIS], PeliculaTerror.SINOPSIS_LEN);
    }

    /**
     * Método para guardar un string en un fichero binario aleatorio.
     * @param f Archivo binario aleatorio.
     * @param str String a guardar.
     * @param size Tamaño del campo en el archivo.
     * @throws IOException
     */
    private static void writeString(RandomAccessFile f, String str, int size) throws IOException {
        StringBuffer sb = new StringBuffer(str);
        sb.setLength(size);
        f.writeChars(sb.toString());
    }

    /**
     * Método para guardar un int en un fichero binario aleatorio.
     * @param f Archivo binario aleatorio.
     * @param nbr String con el número a guardar.
     * @throws IOException
     * @throws InvalidDataException
     */
    private static void writeInt(RandomAccessFile f, String nbr) throws IOException, InvalidDataException {
        try {
            f.writeInt(Integer.parseInt(nbr));
        }
        catch (NumberFormatException e) {
            throw new InvalidDataException("Un valor numérico es inválido");
        }
    }

    // Debug

    /**
     * Método de test para verificar que el amacenamiento es correcto.
     */
    public static void testRead() {
        try {
            RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "r");
            while (f.getFilePointer() < f.length()) {
                System.out.printf(
                    "Peli %d\n  - T: %s\n  - A: %d\n  - D: %d\n  - Dire: %s\n  - S: %s\n\n",
                    f.readInt(),
                    readString(f, PeliculaTerror.TITULO_LEN),
                    f.readInt(),
                    f.readInt(),
                    readString(f, PeliculaTerror.DIRECTOR_LEN),
                    readString(f, PeliculaTerror.SINOPSIS_LEN)
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para leer un String de un tamaño definido.
     * @param f Archivo binario aleatorio.
     * @param size Tamaño del String.
     * @return String resultante.
     * @throws IOException
     */
    private static String readString(RandomAccessFile f, int size) throws IOException {
        int byteSize = size * ByteUtils.sizeof('a');
        byte[] s = new byte[byteSize];
        f.readFully(s, 0, byteSize);
        return ByteUtils.toString(s).trim();
    }
}
