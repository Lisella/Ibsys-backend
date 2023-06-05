package de.Ibsys.ibsys.WorkingTimes;

import java.util.ArrayList;

public class Workplace {
    int id;
    int duration;
    // workplaceId, productId, durationPerUnit, setupTime für jedes Produkt, welches
    // an diesem Arbeitsplatz hergestellt wird
    ArrayList<WorkplaceProductMerge> durationsforeachProductWorkplace;

    public Workplace(int id, int duration, ArrayList<WorkplaceProductMerge> durationsforeachProductWorkplace) {
        this.id = id;
        this.duration = duration;
        // In diese Liste nur die WorkplaceProductMerge-Objekte einfügen, die für diesen
        // Arbeitsplatz gefertigt werden
        this.durationsforeachProductWorkplace = durationsforeachProductWorkplace;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<WorkplaceProductMerge> getDurationsforeachProductWorkplace() {
        return durationsforeachProductWorkplace;
    }
}
