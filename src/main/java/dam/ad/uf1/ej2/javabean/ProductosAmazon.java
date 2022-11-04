package dam.ad.uf1.ej2.javabean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Productos de Amazon.
 *
 * @author Jorge Re -- Jkutkut
 */
public class ProductosAmazon implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<ProductoAmazon> productos;

    public ProductosAmazon() {
        this.productos = new ArrayList<>();
    }

    public void addProductoAmazon(ProductoAmazon p) {
        productos.add(p);
    }
    public ArrayList<ProductoAmazon> getProductos() {
        return productos;
    }
}
