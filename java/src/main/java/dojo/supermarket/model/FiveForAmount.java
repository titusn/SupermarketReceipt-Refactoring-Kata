package dojo.supermarket.model;

public class FiveForAmount implements SpecialOffer {
    @Override
    public String generateDescription(Double amount) {
        return "5 for " + amount.toString();
    }
}
