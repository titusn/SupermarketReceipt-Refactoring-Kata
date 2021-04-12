package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities().keySet()) {
            Discount discount = handleOffersForProduct(offers, catalog, product);
            if (discount != null)
                receipt.addDiscount(discount);
        }
    }

    private Discount handleOffersForProduct(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        if (offers.containsKey(p)) {
            return determineDiscountForProduct(offers, catalog, p);
        }
        return null;
    }

    private Discount determineDiscountForProduct(Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        double quantity = productQuantities.get(p);
        Offer offer = offers.get(p);
        double unitPrice = catalog.getUnitPrice(p);
        int divisor;
        if (offer.offerType == SpecialOfferType.TwoForAmount && (int) quantity >= 2) {
            divisor = 2;
            double discountAmount = calculateDiscountAmount(quantity, offer, unitPrice, divisor);
            return new Discount(p, "2 for " + offer.argument, -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.ThreeForTwo && (int) quantity >= 3) {
            divisor = 3;
            double discountAmount = calculateDiscountAmount(quantity, offer, unitPrice, divisor, 2);
            return new Discount(p, "3 for 2", -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && (int) quantity >= 5) {
            divisor = 5;
            double discountAmount = calculateDiscountAmount(quantity, offer, unitPrice, divisor);
            return new Discount(p, divisor + " for " + offer.argument, -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            return new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        }
        return null;
    }

    private double calculateDiscountAmount(double quantity, Offer offer, double unitPrice, int divisor) {
        int intDivision = getNumberOfTimesOfferApplies((int) quantity, divisor);
        double pricePerUnit = offer.argument * intDivision;
        double theTotal = ((int) quantity % divisor) * unitPrice;
        double total = pricePerUnit + theTotal;
        return unitPrice * quantity - total;
    }

    private double calculateDiscountAmount(double quantity, Offer offer, double unitPrice, int divisor, int multiplier) {
        int intDivision = getNumberOfTimesOfferApplies((int) quantity, divisor);
        double pricePerUnit = offer.argument * intDivision * multiplier;
        double theTotal = ((int) quantity % divisor) * unitPrice;
        double total = pricePerUnit + theTotal;
        return unitPrice * quantity - total;
    }

    private int getNumberOfTimesOfferApplies(int quantityAsInt, int divisor) {
        return quantityAsInt / divisor;
    }
}
