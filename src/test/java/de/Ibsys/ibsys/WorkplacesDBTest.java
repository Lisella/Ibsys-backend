package de.Ibsys.ibsys;

import de.Ibsys.ibsys.WorkingTimes.Workplace;
import de.Ibsys.ibsys.database.WorkplacesDB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WorkplacesDBTest {

    @Test
    public void testGetWorkplaces() {
        ArrayList<Workplace> workplaces = WorkplacesDB.getWorkplaces();
        assertEquals(14, workplaces.size());
        System.out.println(workplaces.size());
    }
}