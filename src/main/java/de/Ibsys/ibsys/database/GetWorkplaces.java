package de.Ibsys.ibsys.database;

import java.util.ArrayList;

import de.Ibsys.ibsys.WorkingTimes.Workplace;
import de.Ibsys.ibsys.WorkingTimes.WorkplaceProductMerge;

public class GetWorkplaces {
    public static ArrayList<Workplace> getWorkplaces() {
        // todo db Call

        // get all workplaces from db
        ArrayList<Workplace> workplaces = new ArrayList<>();

        // foreach workplace
        // get all workplaceProductMerges from db
        ArrayList<WorkplaceProductMerge> workplaceProductMerges = new ArrayList<WorkplaceProductMerge>();

        WorkplaceProductMerge workplaceProductMerge = new WorkplaceProductMerge(1, 1, 1, 30);
        workplaceProductMerges.add(workplaceProductMerge);

        Workplace workplace = new Workplace(1, 0, workplaceProductMerges);

        workplaces.add(workplace);
        return workplaces;
    }
}
