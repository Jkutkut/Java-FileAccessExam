package dam.ad.uf1.ej2;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import dam.ad.uf1.ej2.javabean.ProductoAmazon;
import dam.ad.uf1.ej2.javabean.ProductosAmazon;
import jkutkut.Exception.InvalidDataException;

import java.io.*;
import java.util.ArrayList;

/**
 * Programa que permite pasar un archivo con productos de Amazon en XML a otro binario secuencial.
 *
 * @author Jorge Re -- Jkutkut
 */
public class GenerarProductosAmazonBin {
    private static final String INPUT_FILE = "res/ProductosAmazon.xml";
    private static final String OUTPUT_FILE = "res/ProductosMasVendidos.dat";

    public static void main(String[] args) {
        ProductosAmazon productosAmazon;
        try {
            System.out.println("Leyendo " + INPUT_FILE);
            productosAmazon = loadData();
            System.out.println("Productos listos para guardarse en " + OUTPUT_FILE);
            storeData(productosAmazon.getProductos());
            System.out.println("Productos guardados correctamente");
        }
        catch (FileNotFoundException | InvalidDataException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Debug
        // testRead();
    }

    /**
     * Intenta cargar en memoria los objetos del archivo XML usando XStream.
     * @return Clase con los productos.
     * @throws FileNotFoundException
     */
    private static ProductosAmazon loadData() throws FileNotFoundException {
        XStream xstream = new XStream();

        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);

        xstream.allowTypes(new Class[]{ProductosAmazon.class, ProductoAmazon.class});
        xstream.allowTypesByWildcard(new String[]{
                "dam.ad.uf1.ej2.model.*"
        });

        xstream.alias("mas_vendidos", ProductosAmazon.class);
        xstream.alias("producto", ProductoAmazon.class);

        xstream.addImplicitCollection(ProductosAmazon.class, "productos");

        FileInputStream fis = new FileInputStream(INPUT_FILE);
        return (ProductosAmazon) xstream.fromXML(fis);
    }

    /**
     * Intenta almacenar los productos dados en un archivo binario secuencial.
     * @param productos productos de Amazon.
     * @throws IOException
     */
    private static void storeData(ArrayList<ProductoAmazon> productos) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE));
        for (ProductoAmazon p : productos)
            oos.writeObject(p);
    }

    // Debug

    /**
     * Método de test para verificar el correcto funcionamiento del programa.
     */
    private static void testRead() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE));
            while (true) {
                System.out.println(ois.readObject());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // End of file
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
