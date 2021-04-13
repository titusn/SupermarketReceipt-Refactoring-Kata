package dojo.supermarket.model;

abstract class SpecialOffer {
    abstract String generateDescription(Double amount);
    abstract Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice);
}
