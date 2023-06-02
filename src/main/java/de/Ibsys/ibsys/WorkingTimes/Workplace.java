package de.Ibsys.ibsys.WorkingTimes;

import java.util.ArrayList;

public class Workplace {
    int id;
    int duration;

    ArrayList<WorkplaceProductMerge> durationsforeachProductWorkplace;

    public Workplace(int id, int duration, ArrayList<WorkplaceProductMerge> durationsforeachProductWorkplace) {
        this.id = id;
        this.duration = duration;
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

    public static ArrayList<Workplace> getWorkplaces() {
        // todo db Call
        ArrayList<WorkplaceProductMerge> workplaceProductMerges = new ArrayList<WorkplaceProductMerge>();

        WorkplaceProductMerge workplaceProductMerge = new WorkplaceProductMerge(1, 1, 1);
        workplaceProductMerges.add(workplaceProductMerge);

        Workplace workplace = new Workplace(1, 0, workplaceProductMerges);

        ArrayList<Workplace> workplaces = new ArrayList<>();
        workplaces.add(workplace);
        return workplaces;
    }
}
