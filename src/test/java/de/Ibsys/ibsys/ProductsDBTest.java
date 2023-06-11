package de.Ibsys.ibsys;

import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.database.ProductsDB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductsDBTest {

    @Test
    public void testGetProducts() {
        ArrayList<Product> products = ProductsDB.getProducts();
        assertEquals(29, products.size());
        System.out.println(products.size());
    }
}