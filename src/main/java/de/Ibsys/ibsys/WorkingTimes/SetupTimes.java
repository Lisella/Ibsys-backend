package de.Ibsys.ibsys.WorkingTimes;

public class SetupTimes {

    public int productId;
    public int setupTime;

    public SetupTimes(int productId, int setupTime) {
        this.productId = productId;
        this.setupTime = setupTime;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getSetupTime() {
        return this.setupTime;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setSetupTime(int setupTime) {
        this.setupTime = setupTime;
    }
}
