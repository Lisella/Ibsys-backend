package de.Ibsys.ibsys;

import de.Ibsys.ibsys.WorkingTimes.WaitingListItem;
import de.Ibsys.ibsys.database.WaitingListForWorkstationsDB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WaitingListForWorkstationsDBTest {

    @Test
    public void testGetWaitingList() {
        ArrayList<WaitingListItem> waitingListItems = WaitingListForWorkstationsDB.getWaitingListForWorkstations();
        assertEquals(14, waitingListItems.size());
        System.out.println(waitingListItems.size());
    }

    @Test
    public void testUpdateWaitingList() {
        HashMap<Integer, Integer> waitingListItems = new HashMap<>();
        waitingListItems.put(1, 780);
        waitingListItems.put(2, 200);
        waitingListItems.put(3, 540);
        waitingListItems.put(4, 0);
        waitingListItems.put(6, 90);
        waitingListItems.put(7, 60);
        waitingListItems.put(8, 90);
        waitingListItems.put(9, 560);
        waitingListItems.put(10, 0);
        waitingListItems.put(11, 0);
        waitingListItems.put(12, 0);
        waitingListItems.put(13, 0);
        waitingListItems.put(14, 510);
        waitingListItems.put(15, 330);

        WaitingListForWorkstationsDB.updateWaitingListForWorkstations(waitingListItems);
        System.out.println("Updated WaitingList:" + waitingListItems);
    }

}
