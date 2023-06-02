package de.Ibsys.ibsys.WorkingTimes;

import de.Ibsys.ibsys.Production.ProductionItem;

import java.util.ArrayList;

import static de.Ibsys.ibsys.WorkingTimes.Workplace.getWorkplaces;

public class Calculations {
    public static ArrayList<WorkingTime> CalculateWorkingtimesByProductionList(
            ArrayList<ProductionItem> productionList) {

        System.out.println("Beginne die Überstundenberechnung");
        System.out.println("Hole alle Arbeitsplätze und zugehörige Produktionszeiten pro Produkt");
        System.out.println("----------------------");

        ArrayList<Workplace> workplaces = getWorkplaces();

        System.out.println("Berechne die benötigte Zeit je Arbeitsplatz");
        System.out.println("----------------------");
        for (Workplace workplace : workplaces) {
            for (WorkplaceProductMerge workplaceProductMerge : workplace.durationsforeachProductWorkplace) {
                workplace.duration = workplaceProductMerge.durationPerUnit * getProductionQuantityByProductId(
                        productionList, workplaceProductMerge.getProductId());
                System.out.println(
                        workplace.id + " : " + workplace.duration);

            }
        }

        System.out.println("----------------------");
        System.out.println("Berechne die benötigte Überstunden je Arbeitsplatz");

        ArrayList<WorkingTime> workingTimes = new ArrayList<WorkingTime>();

        for (Workplace workplace : workplaces) {
            int shifts = 0;
            int overtime = 0;
            // berechne die Schichten und Überminuten für jeden Arbeitsplatz.
            // Eine Schicht dauert 2400 Minuten
            // Es können maximal 3 Schichten pro Tag gearbeitet werden
            // es können maximal 1200 Minuten überstunden pro Woche gearbeitet werden. Aber
            // nur, wenn max 2 Schichten gefahren werden.
            // 2400 Minuten pro Schicht * 3 Schichten pro Tag * 5 Tage pro Woche = 21600
            // Minuten pro Woche

            // Berechne die Schichten (max 3)
            if (workplace.duration >= 7200) {
                shifts = 3;
                workingTimes.add(new WorkingTime(workplace.id, shifts, 0));
                break;
            } else if (workplace.duration >= 4800) {
                shifts = 2;
                workplace.duration -= 4800;
            } else if (workplace.duration >= 2400) {
                shifts = 1;
                workplace.duration -= 2400;
            } else {
                // todo are 0 Shifts and only overtimes allowed?
                shifts = 0;
            }

            if (workplace.duration > 800) {
                shifts++;
                overtime = 0;
                workplace.duration -= 2400;
            }

            if (workplace.duration > 0) {
                overtime = workplace.duration;
            }

            workingTimes.add(new WorkingTime(workplace.id, shifts, overtime / 5));
            System.out.println(workplace.id + " : " + shifts + " Schichten, " + overtime + " Überminuten");
        }

        return workingTimes;
    }

    private static int getProductionQuantityByProductId(ArrayList<ProductionItem> productionList,
            int productId) {
        for (ProductionItem productionItem : productionList) {
            if (productionItem.getArticle() == productId) {
                return productionItem.getQuantity();
            }
        }
        return 0; // Default duration if no matching product ID is found
    }

}
