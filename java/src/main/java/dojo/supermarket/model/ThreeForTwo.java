package dojo.supermarket.model;

public class ThreeForTwo extends SpecialOffer {

    @Override
    public String generateDescription(Double amount) {
        return "3 for 2";
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return null;
    }
}
