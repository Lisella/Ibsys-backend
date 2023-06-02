package de.Ibsys.ibsys.WorkingTimes;

import de.Ibsys.ibsys.Production.ProductionItem;

import java.util.ArrayList;

import static de.Ibsys.ibsys.WorkingTimes.Workplace.getWorkplaces;

public class Calculations {
    private static ArrayList<Workplace> CalculateWorkingtimesByProductionList(ArrayList<ProductionItem> productionList){

        System.out.println("Beginne die Überstundenberechnung");
        System.out.println("Hole alle Arbeitsplätze und zugehörige Produktionszeiten pro Produkt");

        ArrayList<Workplace> workplaces = getWorkplaces();

        for (Workplace workplace: workplaces){
            for (WorkplaceProductMerge workplaceProductMerge: workplace.durationsforeachProductWorkplace) {
                workplace.duration = workplaceProductMerge.durationPerUnit * getDurationForProductgetQuantityforProduct(productionList, workplaceProductMerge.getProductId());
            }
        }

        System.out.println("Berechne die benötigte Zeit je Arbeitsplatz");

        return workplaces;
    }

    private static int getDurationForProductgetQuantityforProduct(ArrayList<ProductionItem> productionList, int productId) {
        for (ProductionItem productionItem : productionList) {
            if (productionItem.getArticle() == productId) {
                return productionItem.getQuantity();
            }
        }
        return 0; // Default duration if no matching product ID is found
    }

}
