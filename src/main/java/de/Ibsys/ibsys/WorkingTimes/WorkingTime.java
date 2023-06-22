package de.Ibsys.ibsys.WorkingTimes;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;

public class WorkingTime {
    private int station;
    private int shift;
    private int overtime;
    public ArrayList<ProductionTimes> productionTimes;
    public ArrayList<SetupTimes> setupTimes;
    public int overallDuration;

    public WorkingTime(int station, int shift, int overtime) {
        this.station = station;
        this.shift = shift;
        this.overtime = overtime;
        this.productionTimes = new ArrayList<ProductionTimes>();
        this.setupTimes = new ArrayList<SetupTimes>();
        this.overallDuration = 0;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public void addProductionTime(int productId, int quantity, int durationPerUnit) {
        ProductionTimes productionTime = new ProductionTimes(productId, quantity, durationPerUnit);
        this.productionTimes.add(productionTime);
    }

    public void addSetupTime(int productId, int setupTime) {
        SetupTimes setupTimeObject = new SetupTimes(productId, setupTime);
        this.setupTimes.add(setupTimeObject);
    }

    public int getOverallDuration() {
        return overallDuration;
    }

    public void setOverallDuration(int overallDuration) {
        this.overallDuration = overallDuration;
    }
}
