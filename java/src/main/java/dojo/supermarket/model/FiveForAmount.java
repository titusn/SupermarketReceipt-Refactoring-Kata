package dojo.supermarket.model;

public class FiveForAmount extends SpecialOffer {

    @Override
    String generateDescription(Double amount) {
        return "5 for " + amount.toString();
    }

    @Override
    Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 5);
    }
}
