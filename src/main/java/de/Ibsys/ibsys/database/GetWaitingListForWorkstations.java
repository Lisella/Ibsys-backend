package de.Ibsys.ibsys.database;

import java.util.ArrayList;

import de.Ibsys.ibsys.WorkingTimes.WaitingListItem;

public class GetWaitingListForWorkstations {
    public static ArrayList<WaitingListItem> getWaitingListForWorkstations() {
        // todo db Call
        ArrayList<WaitingListItem> waitingListItems = new ArrayList<WaitingListItem>();

        int workplaceId = 1;
        int waitingTime = 2500;

        WaitingListItem waitingListItem = new WaitingListItem(workplaceId, waitingTime);
        waitingListItems.add(waitingListItem);

        return waitingListItems;
    }
}
