package dojo.supermarket.model.specialoffer;

public class ThreeForTwo extends SpecialOffer {

    @Override
    public String generateDescription(Double amount) {
        return "3 for 2";
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 3, 2);
    }

    @Override
    public boolean isApplicable(double quantity) {
        return false;
    }
}
