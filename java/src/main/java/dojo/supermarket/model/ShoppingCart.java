package dojo.supermarket.model;

import dojo.supermarket.model.specialoffer.*;

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
        if (offer.isApplicable(quantity)) {
            SpecialOffer specialOffer = new TwoForAmount();
            double discountAmount = specialOffer.calculateDiscountAmount(quantity, offer.argument, unitPrice);
            return new Discount(p, offer.generateDescription(), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.ThreeForTwo && (int) quantity >= 3) {
            SpecialOffer specialOffer = new ThreeForTwo();
            double discountAmount = specialOffer.calculateDiscountAmount(quantity, offer.argument, unitPrice);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && (int) quantity >= 5) {
            SpecialOffer specialOffer = new FiveForAmount();
            double discountAmount = specialOffer.calculateDiscountAmount(quantity, offer.argument, unitPrice);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            SpecialOffer specialOffer = new PercentDiscount();
            double discountAmount = specialOffer.calculateDiscountAmount(quantity, offer.argument, unitPrice);
            return new Discount(p, specialOffer.generateDescription(offer.argument), -discountAmount);
        }
        return null;
    }
}
