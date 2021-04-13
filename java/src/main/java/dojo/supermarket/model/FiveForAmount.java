package dojo.supermarket.model;

public class FiveForAmount implements SpecialOffer {
    @Override
    public String generateDescription(Double amount) {
        return "5 for " + amount.toString();
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return null;
    }
}
