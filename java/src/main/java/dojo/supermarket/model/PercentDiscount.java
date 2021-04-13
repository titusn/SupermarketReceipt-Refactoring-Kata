package dojo.supermarket.model;

public class PercentDiscount implements SpecialOffer {
    @Override
    public String generateDescription(Double amount) {
        return amount.toString() + "% off";
    }
}
