package dojo.supermarket.model.specialoffer;

 public class PercentDiscount extends SpecialOffer {

     @Override
     public String generateDescription(Double amount) {
        return amount.toString() + "% off";
     }

     @Override
     public Double calculateDiscountAmount(double quantity, double percentage, double unitPrice) {
         return quantity * unitPrice * percentage / 100.0;
    }
}
