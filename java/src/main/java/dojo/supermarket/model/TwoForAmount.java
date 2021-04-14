package dojo.supermarket.model;

class TwoForAmount extends SpecialOffer{

    @Override
    String generateDescription(Double amount) {
        return "2 for " + amount.toString();
    }

    @Override
    Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return calculateDiscountAmount(quantity, offerAmount, unitPrice, 2);
    }

}
