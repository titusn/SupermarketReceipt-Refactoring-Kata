package dojo.supermarket.model.specialoffer;

public class FiveForAmount extends SpecialOffer {

    @Override
    public String generateDescription(Double amount) {
        return "5 for " + amount.toString();
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 5);
    }

    @Override
    public boolean isApplicable(double quantity) {
        return false;
    }
}
