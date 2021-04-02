package dojo.supermarket.model;

public enum SpecialOfferType implements Description{
    THREE_FOR_TWO("3 for 2"),
    TEN_PERCENT_DISCOUNT("% off"),
    TWO_FOR_AMOUNT("2 for "),
    FIVE_FOR_AMOUNT(" for ");

    private String description;

    SpecialOfferType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
