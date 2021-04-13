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
            SpecialOffer specialOffer = new TwoForAmount();
            double discountAmount = calculateDiscountAmount(quantity, offer.argument, unitPrice, divisor);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.ThreeForTwo && (int) quantity >= 3) {
            divisor = 3;
            SpecialOffer specialOffer = new ThreeForTwo();
            double discountAmount = calculateDiscountAmount(quantity, offer.argument, unitPrice, divisor, 2);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && (int) quantity >= 5) {
            divisor = 5;
            SpecialOffer specialOffer = new FiveForAmount();
            double discountAmount = calculateDiscountAmount(quantity, offer.argument, unitPrice, divisor);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            SpecialOffer specialOffer = new PercentDiscount();
            double discountAmount = calculateDiscountAmountFromPercentage(quantity, offer.argument, unitPrice);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        return null;
    }

    private double calculateDiscountAmountFromPercentage(double quantity, double percentage, double unitPrice) {
        return quantity * unitPrice * percentage / 100.0;
    }

    private double calculateDiscountAmount(double quantity, double offerPrice, double unitPrice, int divisor) {
        return calculateDiscountAmount(quantity, offerPrice, unitPrice, divisor, 1);
    }

    private double calculateDiscountAmount(double quantity, double offerPrice, double unitPrice, int divisor, int multiplier) {
        double subtotalDiscounted = offerPrice * getNumberOfTimesOfferApplies((int) quantity, divisor) * multiplier;
        double subtotalAboveDiscount = ((int) quantity % divisor) * unitPrice;
        double total = subtotalDiscounted + subtotalAboveDiscount;
        return unitPrice * quantity - total;
    }

    private int getNumberOfTimesOfferApplies(int quantityAsInt, int divisor) {
        return quantityAsInt / divisor;
    }
}
