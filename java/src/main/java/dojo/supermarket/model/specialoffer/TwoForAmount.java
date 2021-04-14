package dojo.supermarket.model.specialoffer;

public class TwoForAmount extends SpecialOffer{

    @Override
    public String generateDescription(Double amount) {
        return "2 for " + amount.toString();
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 2);
    }

    @Override
    public boolean isApplicable(double quantity) {
        return (int) quantity >= 2;
    }
}
