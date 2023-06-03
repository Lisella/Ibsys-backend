package de.Ibsys.ibsys.WorkingTimes;

import jakarta.xml.bind.annotation.XmlAttribute;

public class WorkingTime {
    private int station;
    private int shift;

    public WorkingTime(int station, int shift, int overtime) {
        this.station = station;
        this.shift = shift;
        this.overtime = overtime;
    }

    private int overtime;

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
}
