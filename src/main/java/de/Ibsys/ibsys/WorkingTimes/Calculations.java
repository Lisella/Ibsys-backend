package de.Ibsys.ibsys.WorkingTimes;

import de.Ibsys.ibsys.Production.ProductionItem;
import de.Ibsys.ibsys.database.WaitingListForWorkstationsDB;
import de.Ibsys.ibsys.database.WorkplacesDB;

import java.util.ArrayList;

public class Calculations {
    public static ArrayList<WorkingTime> CalculateWorkingtimesByProductionList(
            ArrayList<ProductionItem> productionList) {

        System.out.println("Hole alle Arbeitsplätze und zugehörige Produktionszeiten pro Produkt");
        System.out.println("----------------------");

        ArrayList<Workplace> workplaces = WorkplacesDB.getWorkplaces();

        for (Workplace workplace : workplaces) {
            for (WorkplaceProductMerge workplaceProductMerge : workplace.durationsforeachProductWorkplace) {
                int quantity = getProductionQuantityByProductId(productionList, workplaceProductMerge.getProductId());
                workplace.duration += workplaceProductMerge.durationPerUnit * quantity;
                workplace.productionTimes.add(new ProductionTimes(workplaceProductMerge.getProductId(),
                        quantity, workplaceProductMerge.durationPerUnit));
                System.out.println("Arbeitsplatz: " + workplace.id + " Produkt: " + workplaceProductMerge.getProductId()
                        + " Menge: " + quantity + " Dauer: " + workplaceProductMerge.durationPerUnit * quantity);
            }
        }
        System.out.println("----------------------");

        System.out.println(
                "Berechne die benötigte Zeit je Arbeitsplatz für diese Periode ohne Warteliste und Rüstzeiten");
        System.out.println("----------------------");
        // gebe die benötigte Zeit je Arbeitsplatz aus
        for (Workplace workplace : workplaces) {
            System.out.println(
                    workplace.id + " : " + workplace.duration);
        }
        System.out.println("----------------------");
        System.out.println(
                "Berechne die benötigte Zeit je Arbeitsplatz unter Berücksichtigung der Warteliste der letzten Periode");
        System.out.println("----------------------");
        // Füge die Zeiten der Waitinglist hinzu
        ArrayList<WaitingListItem> waitingList = WaitingListForWorkstationsDB.getWaitingListForWorkstations();

        // gebe die Warteliste aus
        for (WaitingListItem waitingListItem : waitingList) {
            System.out.println(
                    waitingListItem.toString());
        }
        for (WaitingListItem waitingListItem : waitingList) {
            for (Workplace workplace : workplaces) {
                if (waitingListItem.workplaceId == workplace.id) {
                    workplace.duration += waitingListItem.waitingTime;
                    workplace.waitingDuration += waitingListItem.waitingTime;
                }
            }
        }

        // gebe die benötigte Zeit je Arbeitsplatz aus
        for (Workplace workplace : workplaces) {
            System.out.println(
                    workplace.id + " : " + workplace.duration);
        }
        System.out.println("----------------------");
        System.out.println("Arbeitszeiten inclusive Warteliste:");
        System.out.println("----------------------");
        // gebe die benötigte Zeit je Arbeitsplatz aus
        for (Workplace workplace : workplaces) {
            System.out.println(
                    workplace.id + " : " + workplace.duration);
        }

        System.out.println("Berechne die Rüstzeiten je Arbeitsplatz");
        // Füge die Rüstzeiten hinzu
        // gehe alle Arbeitsplätze durch
        // gehe alle Fertigungsaufträge durch
        // wenn der Fertigungsauftrag auf dem Arbeitsplatz gefertigt wird, dann addiere
        // die Rüstzeit auf die Gesamtdauer des Arbeitsplatzes
        for (Workplace workplace : workplaces) {
            for (ProductionItem productionItem : productionList) {

                for (WorkplaceProductMerge workplaceProductMerge : workplace.durationsforeachProductWorkplace) {
                    if (productionItem.getArticle() == workplaceProductMerge.getProductId()
                            && workplaceProductMerge.getWorkplaceId() == workplace.id) {
                        System.out.println("Rüstzeit für Produkt " + workplaceProductMerge.getProductId()
                                + " auf Arbeitsplatz " + workplace.id + " beträgt "
                                + workplaceProductMerge.setupTime);
                        if (productionItem.getQuantity() != 0) {
                            workplace.duration += workplaceProductMerge.setupTime;
                            workplace.setupTimes.add(new SetupTimes(workplaceProductMerge.getProductId(),
                                    workplaceProductMerge.setupTime, 1));
                        }
                        if (productionItem.getQuantity() == 0) {
                            workplace.setupTimes.add(new SetupTimes(workplaceProductMerge.getProductId(),
                                    workplaceProductMerge.setupTime, 0));
                        }
                    }
                }

            }
        }

        for (Workplace workplace : workplaces) {
            ArrayList<SetupTimes> newSetupTimes = new ArrayList<SetupTimes>();
            for (SetupTimes setupTimes : workplace.setupTimes) {
                boolean found = false;
                for (SetupTimes newSetupTime : newSetupTimes) {
                    if (newSetupTime.productId == setupTimes.productId) {
                        found = true;
                        newSetupTime.setSetupQunatity(newSetupTime.getSetupQunatity() + 1);
                    }
                }
                if (!found) {
                    newSetupTimes.add(setupTimes);
                }
            }
            workplace.setupTimes = newSetupTimes;
        }

        // gebe die benötigte Zeit je Arbeitsplatz aus nachdem die Rüstzeiten
        // hinzugefügt wurden
        System.out.println("----------------------");
        System.out.println("Final benötigte Zeit je Arbeitsplatz");
        System.out.println("----------------------");
        for (Workplace workplace : workplaces) {
            System.out.println(
                    workplace.id + " : " + workplace.duration);
        }

        System.out.println("----------------------");
        System.out.println("Berechne die benötigte Überstunden je Arbeitsplatz");
        System.out.println("----------------------");

        ArrayList<WorkingTime> workingTimes = new ArrayList<WorkingTime>();

        for (Workplace workplace : workplaces) {
            int copyOverallDuration = workplace.duration;
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
                workingTimes.add(new WorkingTime(workplace.id, shifts, 0, workplace.getProductionTimes(),
                        workplace.getSetupTimes(), workplace.waitingDuration, copyOverallDuration));
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

            workingTimes.add(new WorkingTime(workplace.id, shifts, overtime / 5, workplace.getProductionTimes(),
                    workplace.getSetupTimes(), workplace.waitingDuration, copyOverallDuration));
            System.out.println(workplace.id + " : " + shifts + " Schichten, " + overtime / 5 + " Überminuten pro Tag");
        }

        return workingTimes;
    }

    private static int getProductionQuantityByProductId(ArrayList<ProductionItem> productionList,
            int productId) {
        int quantity = 0;
        for (ProductionItem productionItem : productionList) {
            if (productionItem.getArticle() == productId) {
                quantity += productionItem.getQuantity();
            }
        }
        return quantity;
    }

}
