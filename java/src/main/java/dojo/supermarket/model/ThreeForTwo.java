package dojo.supermarket.model;

public class ThreeForTwo implements SpecialOffer {

    @Override
    public String generateDescription(Double amount) {
        return "3 for 2";
    }
}
