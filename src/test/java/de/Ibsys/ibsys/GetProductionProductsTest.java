package de.Ibsys.ibsys;

import de.Ibsys.ibsys.Production.ProductionProduct;
import de.Ibsys.ibsys.database.GetProductionProducts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetProductionProductsTest {
    @Test
    public void testGetProductionProducts() {
        ArrayList<ProductionProduct> productionProductsproducts = GetProductionProducts.getProductionProducts();
        assertEquals(30, productionProductsproducts.size());
        System.out.println(productionProductsproducts.size());
    }
}
