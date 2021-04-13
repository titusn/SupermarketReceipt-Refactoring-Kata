package dojo.supermarket.model;

public class PercentDiscount extends SpecialOffer {
    @Override
    public String generateDescription(Double amount) {
        return amount.toString() + "% off";
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return null;
    }
}
