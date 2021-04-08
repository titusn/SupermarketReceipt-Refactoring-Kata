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

    void handleOffers(final Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        products().stream().reduce(receipt, (product, r) -> handleDiscountForProduct(r, offers,catalog, product)) ;
        products().forEach(p -> handleDiscountForProduct(receipt, offers, catalog, p));
    }

    private Receipt handleDiscountForProduct(final Receipt receipt, Map<Product, Offer> offers, final SupermarketCatalog catalog, final Product product) {
        if (!offers.containsKey(product)) {
            return receipt;
        }

        Discount discount = determineDiscount(offers, catalog, product);
        if (discount != null)
            receipt.addDiscount(discount);

        return receipt;
    }

    private Discount determineDiscount(final Map<Product, Offer> offers, final SupermarketCatalog catalog, final Product product) {
        double quantity = productQuantities.get(product);
        Offer offer = offers.get(product);
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;

        int divisor = determineDivisor(offer);
        int numberOfXs = determineNumberOfTimesOfferApplies(quantityAsInt, divisor);

        if ((offer.offerType == SpecialOfferType.TwoForAmount) && (quantityAsInt >= 2)) {
            int intDivision = determineNumberOfTimesOfferApplies(quantityAsInt, divisor);
            double pricePerUnit = offer.argument * intDivision;
            double theTotal = (quantityAsInt % 2) * unitPrice;
            double total = pricePerUnit + theTotal;
            double discountN = unitPrice * quantity - total;
            return new Discount(product, "2 for " + offer.argument, -discountN);
        }

        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            return new Discount(product, divisor + " for 2", -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            return new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            return new Discount(product, divisor + " for " + offer.argument, -discountTotal);
        }
        return null;
    }

    private int determineNumberOfTimesOfferApplies(int quantityAsInt, int divisor) {
        return quantityAsInt / divisor;
    }

    private int determineDivisor(Offer offer) {
        int x = 1;
        if (offer.offerType == SpecialOfferType.ThreeForTwo) {
            x = 3;

        } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
            x = 2;
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount) {
            x = 5;
        }
        return x;
    }

    private Set<Product> products() {
        return productQuantities().keySet();
    }
}
