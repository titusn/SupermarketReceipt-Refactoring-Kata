package dojo.supermarket.model;

import java.util.*;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    List<Discount> handleOffers(Map<Product, Offer> offers, SupermarketCatalog catalog) {
        Optional<Discount> discount;
        List<Discount> discountList = new ArrayList<>();
        for (Product p: productQuantities.keySet()) {
            discount = checkOffersForProduct(offers, catalog, p);
            discount.ifPresent(discountList::add);
        }
        return discountList;
    }

    private Optional<Discount> checkOffersForProduct(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        Optional<Discount> discount = Optional.empty();
        double quantity = productQuantities.get(p);
        if (offers.containsKey(p)) {
            Offer offer = offers.get(p);
            double unitPrice = catalog.getUnitPrice(p);
            int quantityAsInt = (int) quantity;
            int x = 1;
            if (offer.offerType == SpecialOfferType.ThreeForTwo) {
                x = 3;

            } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
                x = 2;
                if (quantityAsInt >= 2) {
                    int intDivision = quantityAsInt / x;
                    double pricePerUnit = offer.argument * intDivision;
                    double theTotal = (quantityAsInt % 2) * unitPrice;
                    double total = pricePerUnit + theTotal;
                    double discountN = unitPrice * quantity - total;
                    discount = Optional.of(new Discount(p, "2 for " + offer.argument, -discountN));
                }

            } if (offer.offerType == SpecialOfferType.FiveForAmount) {
                x = 5;
            }
            int numberOfXs = quantityAsInt / x;
            if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                discount = Optional.of(new Discount(p, "3 for 2", -discountAmount));
            }
            if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
                discount = Optional.of(new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0));
            }
            if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                discount = Optional.of(new Discount(p, x + " for " + offer.argument, -discountTotal));
            }

        }
        return discount;
    }


}
