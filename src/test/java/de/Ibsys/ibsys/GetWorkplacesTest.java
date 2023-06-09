package de.Ibsys.ibsys;

import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.WorkingTimes.Workplace;
import de.Ibsys.ibsys.database.GetProducts;
import de.Ibsys.ibsys.database.GetWorkplaces;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GetWorkplacesTest {

    @Test
    public void testGetWorkplaces() {
        ArrayList<Workplace> workplaces = GetWorkplaces.getWorkplaces();
        assertEquals(14, workplaces.size());
        System.out.println(workplaces.size());
    }
}