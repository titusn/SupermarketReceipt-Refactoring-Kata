package dojo.supermarket.model;

public enum SpecialOfferType implements Description{
    THREE_FOR_TWO("3 for 2") {
        @Override
        boolean applies(int quantityAsInt) {
             return quantityAsInt >= 3;
        }

        @Override
        String generateDescription(double argument) {
            return getDescription();
        }
    },
    TEN_PERCENT_DISCOUNT("% off") {
        @Override
        boolean applies(int quantityAsInt) {
            return true;
        }

        @Override
        String generateDescription(double argument) {
            return argument + getDescription();
        }
    },
    TWO_FOR_AMOUNT("2 for ") {
        @Override
        boolean applies(int quantityAsInt) {
            return quantityAsInt >= 2;
        }

        @Override
        String generateDescription(double argument) {
            return getDescription() + argument;
        }
    },
    FIVE_FOR_AMOUNT("5 for ") {
        @Override
        boolean applies(int quantityAsInt) {
            return quantityAsInt >= 5;
        }

        @Override
        String generateDescription(double argument) {
            return getDescription() + argument;
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

    abstract String generateDescription(double argument);
}
