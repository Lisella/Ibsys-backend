package de.Ibsys.ibsys.Production;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.database.ProductionProductsDB;
import de.Ibsys.ibsys.database.WaitingListProductsDB;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<ProductionItem> createProductionByProductionPlanning(
            ArrayList<ProductionPlanEntity> productionPlan, ArrayList<ReserveStockProduct> reserveStockProducts) {

        ArrayList<ProductionItem> productionItems = new ArrayList<>();
        ArrayList<WaitingListProduct> waitingListProducts = WaitingListProductsDB.getWaitingListProductsFromDB();
        System.out.println("Für Produktionmengen zuerst alle Produkte die nicht bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<ProductionProduct> products = ProductionProductsDB.getProductionProducts();

        // gebe alle Produkte aus, mit dem aktuellen Lagerbestand
        for (ProductionProduct product : products) {
            for (ReserveStockProduct r : reserveStockProducts) {
                if (product.getId() == r.getProductId()) {
                    for (WaitingListProduct w : waitingListProducts) {
                        if (product.getId() == w.getProductId()) {
                            product.setWaitingListQuantity(w.getWaitlistQuantity());
                            product.setInProductionQuantity(w.getInWorkQuantity());
                            product.setReserveStock(r.getReserveStock());
                        }
                    }

                }

            }
        }

        // bestimme für Fertigungsaufträge für p1
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        int p4 = 0;
        int p5 = 0;
        int p6 = 0;
        int p7 = 0;
        int p8 = 0;
        int p9 = 0;
        int p10 = 0;
        int p11 = 0;
        int p12 = 0;
        int p13 = 0;
        int p14 = 0;
        int p15 = 0;
        int p16 = 0;
        int p17 = 0;
        int p18 = 0;
        int p19 = 0;
        int p20 = 0;
        int p26 = 0;
        int p29 = 0;
        int p30 = 0;
        int p31 = 0;
        int p49 = 0;
        int p50 = 0;
        int p51 = 0;
        int p54 = 0;
        int p55 = 0;
        int p56 = 0;

        ProductionProduct p1Product = getProductById(1, products);
        ProductionProduct p2Product = getProductById(2, products);
        ProductionProduct p3Product = getProductById(3, products);
        ProductionProduct p4Product = getProductById(4, products);
        ProductionProduct p5Product = getProductById(5, products);
        ProductionProduct p6Product = getProductById(6, products);
        ProductionProduct p7Product = getProductById(7, products);
        ProductionProduct p8Product = getProductById(8, products);
        ProductionProduct p9Product = getProductById(9, products);
        ProductionProduct p10Product = getProductById(10, products);
        ProductionProduct p11Product = getProductById(11, products);
        ProductionProduct p12Product = getProductById(12, products);
        ProductionProduct p13Product = getProductById(13, products);
        ProductionProduct p14Product = getProductById(14, products);
        ProductionProduct p15Product = getProductById(15, products);
        ProductionProduct p16Product = getProductById(16, products);
        ProductionProduct p17Product = getProductById(17, products);
        ProductionProduct p18Product = getProductById(18, products);
        ProductionProduct p19Product = getProductById(19, products);
        ProductionProduct p20Product = getProductById(20, products);
        ProductionProduct p26Product = getProductById(26, products);
        ProductionProduct p29Product = getProductById(29, products);
        ProductionProduct p30Product = getProductById(30, products);
        ProductionProduct p31Product = getProductById(31, products);
        ProductionProduct p49Product = getProductById(49, products);
        ProductionProduct p50Product = getProductById(50, products);
        ProductionProduct p51Product = getProductById(51, products);
        ProductionProduct p54Product = getProductById(54, products);
        ProductionProduct p55Product = getProductById(55, products);
        ProductionProduct p56Product = getProductById(56, products);

        p1 += productionPlan.get(0).product1Consumption + p1Product.getReserveStock() - p1Product.getStock()
                - p1Product.getWaitingListQuantity() - p1Product.getInProductionQuantity();

        p2 += productionPlan.get(0).product2Consumption + p2Product.getReserveStock() - p2Product.getStock()
                - p2Product.getWaitingListQuantity() - p2Product.getInProductionQuantity();

        p3 += productionPlan.get(0).product3Consumption + p3Product.getReserveStock() - p3Product.getStock()
                - p3Product.getWaitingListQuantity() - p3Product.getInProductionQuantity();

        p26 = p1 + p1Product.getWaitingListQuantity() + p2 + p2Product.getWaitingListQuantity() + p3
                + p3Product.getWaitingListQuantity() + p26Product.getReserveStock() - p26Product.getStock()
                - p26Product.getWaitingListQuantity() - p26Product.getInProductionQuantity();

        p51 += p1 + p1Product.getWaitingListQuantity() + p51Product.getReserveStock() - p51Product.getStock()
                - p51Product.getWaitingListQuantity() - p51Product.getInProductionQuantity();

        p56 += p2 + p2Product.getWaitingListQuantity() + p56Product.getReserveStock() - p56Product.getStock()
                - p56Product.getWaitingListQuantity() - p56Product.getInProductionQuantity();

        p31 += p3 + p3Product.getWaitingListQuantity() + p31Product.getReserveStock() - p31Product.getStock()
                - p31Product.getWaitingListQuantity() - p31Product.getInProductionQuantity();

        p16 += p51 + p51Product.getWaitingListQuantity() + p56 + p56Product.getWaitingListQuantity() + p31
                + p31Product.getWaitingListQuantity() + p16Product.getReserveStock() - p16Product.getStock()
                - p16Product.getWaitingListQuantity() - p16Product.getInProductionQuantity();

        p17 += p51 + p51Product.getWaitingListQuantity() + p56 + p56Product.getWaitingListQuantity() + p31
                + p31Product.getWaitingListQuantity() + p17Product.getReserveStock() - p17Product.getStock()
                - p17Product.getWaitingListQuantity() - p17Product.getInProductionQuantity();

        p50 += p51 + p51Product.getWaitingListQuantity() + p50Product.getReserveStock() - p50Product.getStock()
                - p50Product.getWaitingListQuantity() - p50Product.getInProductionQuantity();

        p55 += p56 + p56Product.getWaitingListQuantity() + p55Product.getReserveStock() - p55Product.getStock()
                - p55Product.getWaitingListQuantity() - p55Product.getInProductionQuantity();

        p30 += p31 + p31Product.getWaitingListQuantity() + p30Product.getReserveStock() - p30Product.getStock()
                - p30Product.getWaitingListQuantity() - p30Product.getInProductionQuantity();

        // p1
        p4 += p50 + p50Product.getWaitingListQuantity() + p4Product.getReserveStock() - p4Product.getStock()
                - p4Product.getWaitingListQuantity() - p4Product.getInProductionQuantity();

        p10 = p50 + p50Product.getWaitingListQuantity() + p10Product.getReserveStock() - p10Product.getStock()
                - p10Product.getWaitingListQuantity() - p10Product.getInProductionQuantity();

        p49 += p50 + p50Product.getWaitingListQuantity() + p49Product.getReserveStock() - p49Product.getStock()
                - p49Product.getWaitingListQuantity() - p49Product.getInProductionQuantity();

        p7 = p49 + p49Product.getWaitingListQuantity() + p7Product.getReserveStock() - p7Product.getStock()
                - p7Product.getWaitingListQuantity() - p7Product.getInProductionQuantity();

        p13 = p49 + p49Product.getWaitingListQuantity() + p13Product.getReserveStock() - p13Product.getStock()
                - p13Product.getWaitingListQuantity() - p13Product.getInProductionQuantity();

        p18 = p49 + p49Product.getWaitingListQuantity() + p18Product.getReserveStock() - p18Product.getStock()
                - p18Product.getWaitingListQuantity() - p18Product.getInProductionQuantity();

        // p2

        p5 = p55 + p55Product.getWaitingListQuantity() + p5Product.getReserveStock() - p5Product.getStock()
                - p5Product.getWaitingListQuantity() - p5Product.getInProductionQuantity();

        p11 = p55 + p55Product.getWaitingListQuantity() + p11Product.getReserveStock() - p11Product.getStock()
                - p11Product.getWaitingListQuantity() - p11Product.getInProductionQuantity();

        p54 = p55 + p55Product.getWaitingListQuantity() + p54Product.getReserveStock() - p54Product.getStock()
                - p54Product.getWaitingListQuantity() - p54Product.getInProductionQuantity();

        p8 = p54 + p54Product.getWaitingListQuantity() + p8Product.getReserveStock() - p8Product.getStock()
                - p8Product.getWaitingListQuantity() - p8Product.getInProductionQuantity();

        p14 = p54 + p54Product.getWaitingListQuantity() + p14Product.getReserveStock() - p14Product.getStock()
                - p14Product.getWaitingListQuantity() - p14Product.getInProductionQuantity();

        p19 = p54 + p54Product.getWaitingListQuantity() + p19Product.getReserveStock() - p19Product.getStock()
                - p19Product.getWaitingListQuantity() - p19Product.getInProductionQuantity();

        // p3
        p6 = p30 + p30Product.getWaitingListQuantity() + p6Product.getReserveStock() - p6Product.getStock()
                - p6Product.getWaitingListQuantity() - p6Product.getInProductionQuantity();

        p12 = p30 + p30Product.getWaitingListQuantity() + p12Product.getReserveStock() - p12Product.getStock()
                - p12Product.getWaitingListQuantity() - p12Product.getInProductionQuantity();

        p29 = p30 + p30Product.getWaitingListQuantity() + p29Product.getReserveStock() - p29Product.getStock()
                - p29Product.getWaitingListQuantity() - p29Product.getInProductionQuantity();

        p9 = p29 + p29Product.getWaitingListQuantity() + p9Product.getReserveStock() - p9Product.getStock()
                - p9Product.getWaitingListQuantity() - p9Product.getInProductionQuantity();

        p15 = p29 + p29Product.getWaitingListQuantity() + p15Product.getReserveStock() - p15Product.getStock()
                - p15Product.getWaitingListQuantity() - p15Product.getInProductionQuantity();

        p20 = p29 + p29Product.getWaitingListQuantity() + p20Product.getReserveStock() - p20Product.getStock()
                - p20Product.getWaitingListQuantity() - p20Product.getInProductionQuantity();

        for (ProductionProduct product : products) {

            if (product.getId() == 1) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p1));
            }
            if (product.getId() == 2) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p2));
            }
            if (product.getId() == 3) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p3));
            }
            if (product.getId() == 4) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p4));
            }
            if (product.getId() == 5) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p5));
            }
            if (product.getId() == 6) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p6));
            }
            if (product.getId() == 7) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p7));
            }
            if (product.getId() == 8) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p8));
            }
            if (product.getId() == 9) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p9));
            }

            if (product.getId() == 10) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p10));
            }
            if (product.getId() == 11) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p11));
            }
            if (product.getId() == 12) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p12));
            }
            if (product.getId() == 13) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p13));
            }
            if (product.getId() == 14) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p14));
            }
            if (product.getId() == 15) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p15));
            }
            if (product.getId() == 16) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p16));
            }
            if (product.getId() == 17) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p17));
            }
            if (product.getId() == 18) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p18));
            }
            if (product.getId() == 19) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p19));
            }
            if (product.getId() == 20) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p20));
            }
            if (product.getId() == 26) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p26));
            }
            if (product.getId() == 29) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p29));
            }
            if (product.getId() == 30) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p30));
            }
            if (product.getId() == 31) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p31));
            }
            if (product.getId() == 49) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p49));
            }
            if (product.getId() == 50) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p50));
            }
            if (product.getId() == 51) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p51));
            }
            if (product.getId() == 54) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p54));
            }
            if (product.getId() == 55) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p55));
            }
            if (product.getId() == 56) {
                productionItems.add(new ProductionItem(product.getId(), product.getName(), p56));
            }
        }

        // setzte die Sequenznummer für die Produktionsmengen
        for (int i = 0; i < productionItems.size(); i++) {
            productionItems.get(i).setSequenceNumber(i + 1);
            productionItems.get(i).id = i + 1;
        }

        System.out.println("----------------------");
        System.out.println("Folgende Produktionsmengen werden vorgeschlagen:");
        System.out.println("----------------------");
        for (ProductionItem productionItem : productionItems) {
            System.out.println("Article: " + productionItem.getArticle());
            System.out.println("Quantity: " + productionItem.getQuantity());
            System.out.println("Reihenfolge : " + productionItem.getSequenceNumer());
            System.out.println("----------------------");
        }

        System.out.println("Produktionsmengen Berechnung erfolgreich");

        return productionItems;
    }

    private static ProductionProduct getProductById(int id, ArrayList<ProductionProduct> products) {
        for (ProductionProduct product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
