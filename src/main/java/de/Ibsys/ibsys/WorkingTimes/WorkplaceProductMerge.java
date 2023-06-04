package de.Ibsys.ibsys.WorkingTimes;

public class WorkplaceProductMerge {
    int workplaceId;
    int productId;

    int durationPerUnit;
    int setupTime;

    public int getWorkplaceId() {
        return workplaceId;
    }

    public int getProductId() {
        return productId;
    }

    public int getDurationPerUnit() {
        return durationPerUnit;
    }

    public WorkplaceProductMerge(int workplaceId, int productId, int durationPerUnit, int setupTime) {
        this.workplaceId = workplaceId;
        this.productId = productId;
        this.durationPerUnit = durationPerUnit;
        this.setupTime = setupTime;
    }
}
