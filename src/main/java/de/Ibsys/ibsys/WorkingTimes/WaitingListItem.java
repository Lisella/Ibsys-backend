package de.Ibsys.ibsys.WorkingTimes;

public class WaitingListItem {

    int workplaceId;
    int waitingTime;

    public WaitingListItem(int i, int j) {
        this.workplaceId = i;
        this.waitingTime = j;
    }

    @Override
    public String toString() {
        return "WaitingListItem [id=" + workplaceId + ", timeneed=" + waitingTime + "]";
    }
}
