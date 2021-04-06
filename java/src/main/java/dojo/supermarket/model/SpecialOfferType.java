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

        @Override
        double calculateDiscount(double quantity, double unitPrice, double argument) {
            return calculateDiscountN(quantity, unitPrice, argument, 3, 2);
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

        @Override
        double calculateDiscount(double quantity, double unitPrice, double argument) {
            return calculateDiscountPercent(quantity, unitPrice, argument);
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

        @Override
        double calculateDiscount(double quantity, double unitPrice, double argument) {
            return calculateDiscountN(quantity, unitPrice, argument, 2);
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

        @Override
        double calculateDiscount(double quantity, double unitPrice, double argument) {
            return calculateDiscountN(quantity, unitPrice, argument, 5);
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

    abstract double calculateDiscount(double quantity, double unitPrice, double argument);

    private static double calculateDiscountN(double quantity, double unitPrice, double argument, int divisor) {
        return calculateDiscountN(quantity, unitPrice, argument,  divisor, 1);
    }

    private static double calculateDiscountN(double quantity, double unitPrice, double argument, int divisor, int multiplier) {
        int intDivision = (int) quantity / divisor;
        double pricePerUnit = argument * intDivision * multiplier;
        double totalAboveDiscount = ((int) quantity % divisor) * unitPrice;
        double total = pricePerUnit + totalAboveDiscount;
        return unitPrice * quantity - total;
    }

    private static double calculateDiscountPercent(double quantity, double unitPrice, double argument) {
        return quantity * unitPrice * argument / 100.0;
    }
}
