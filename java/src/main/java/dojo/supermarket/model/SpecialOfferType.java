package dojo.supermarket.model;

public enum SpecialOfferType implements Description{
    THREE_FOR_TWO("3 for 2") {
        @Override
        boolean applies(int quantityAsInt) {
             return quantityAsInt >= 3;
        }
    },
    TEN_PERCENT_DISCOUNT("% off") {
        @Override
        boolean applies(int quantityAsInt) {
            return true;
        }
    },
    TWO_FOR_AMOUNT("2 for ") {
        @Override
        boolean applies(int quantityAsInt) {
            return quantityAsInt >= 2;
        }
    },
    FIVE_FOR_AMOUNT(" for ") {
        @Override
        boolean applies(int quantityAsInt) {
            return quantityAsInt >= 5;
        }
    };

    private String description;

    SpecialOfferType(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    abstract boolean applies(int quantityAsInt);
}
