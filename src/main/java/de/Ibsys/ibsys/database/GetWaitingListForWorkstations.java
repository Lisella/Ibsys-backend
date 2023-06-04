package de.Ibsys.ibsys.database;

import java.util.ArrayList;

import de.Ibsys.ibsys.WorkingTimes.WaitingListItem;

public class GetWaitingListForWorkstations {
    public static ArrayList<WaitingListItem> getWaitingListForWorkstations() {
        // todo db Call
        ArrayList<WaitingListItem> waitingListItems = new ArrayList<WaitingListItem>();

        int productId = 1;
        int waitingTime = 500;

        WaitingListItem waitingListItem = new WaitingListItem(productId, waitingTime);
        waitingListItems.add(waitingListItem);

        return waitingListItems;
    }
}
