package dojo.supermarket.model;

 class PercentDiscount extends SpecialOffer {

     @Override
     String generateDescription(Double amount) {
        return amount.toString() + "% off";
     }

     @Override
     Double calculateDiscountAmount(double quantity, double percentage, double unitPrice) {
         return quantity * unitPrice * percentage / 100.0;
    }
}
