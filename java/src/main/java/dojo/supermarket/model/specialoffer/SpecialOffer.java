package dojo.supermarket.model.specialoffer;

public abstract class SpecialOffer {
    public abstract String generateDescription(Double amount);
    public abstract Double calculateDiscountAmount(double quantity, double offerAmount, double unitPrice);
    public abstract boolean isApplicable(double quantity);

    protected double calculateDiscountAmount(double quantity, double offerPrice, double unitPrice, int divisor) {
        return calculateDiscountAmount(quantity, offerPrice, unitPrice, divisor, 1);
    }

    protected double calculateDiscountAmount(double quantity, double offerPrice, double unitPrice, int divisor, int multiplier) {
        double subtotalDiscounted = offerPrice * getNumberOfTimesOfferApplies((int) quantity, divisor) * multiplier;
        double subtotalAboveDiscount = ((int) quantity % divisor) * unitPrice;
        double total = subtotalDiscounted + subtotalAboveDiscount;
        return unitPrice * quantity - total;
    }

    protected int getNumberOfTimesOfferApplies(int quantityAsInt, int divisor) {
        return quantityAsInt / divisor;
    }

}
