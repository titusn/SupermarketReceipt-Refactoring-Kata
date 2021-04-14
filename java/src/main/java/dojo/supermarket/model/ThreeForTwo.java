package dojo.supermarket.model;

public class ThreeForTwo extends SpecialOffer {

    @Override
    String generateDescription(Double amount) {
        return "3 for 2";
    }

    @Override
    Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 3, 2);
    }
}
