package de.Ibsys.ibsys;

import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.database.GetProducts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetProductsTest {

    @Test
    public void testGetProducts() {
        ArrayList<Product> products = GetProducts.getProducts();
        assertEquals(29, products.size());
        System.out.println(products.size());
    }
}