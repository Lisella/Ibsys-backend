package de.Ibsys.ibsys.WorkingTimes;

public class WorkplaceProductMerge {
    int workplaceId;
    int productId;

    int durationPerUnit;

    public int getWorkplaceId() {
        return workplaceId;
    }

    public int getProductId() {
        return productId;
    }

    public int getDurationPerUnit() {
        return durationPerUnit;
    }

    public WorkplaceProductMerge(int workplaceId, int productId, int durationPerUnit) {
        this.workplaceId = workplaceId;
        this.productId = productId;
        this.durationPerUnit = durationPerUnit;
    }
}
