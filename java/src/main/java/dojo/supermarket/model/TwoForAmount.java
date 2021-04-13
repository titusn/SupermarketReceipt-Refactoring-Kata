package dojo.supermarket.model;

public class TwoForAmount implements SpecialOffer{

    @Override
      public String generateDescription(Double amount) {
        return "2 for " + amount.toString();
    }

    @Override
    public Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice) {
        return null;
    }
}
