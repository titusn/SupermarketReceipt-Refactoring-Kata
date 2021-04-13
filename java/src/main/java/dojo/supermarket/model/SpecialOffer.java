package dojo.supermarket.model;

public interface SpecialOffer {
    String generateDescription(Double amount);
    Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice);
}
