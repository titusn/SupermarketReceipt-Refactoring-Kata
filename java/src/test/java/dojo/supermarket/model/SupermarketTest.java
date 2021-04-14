package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import dojo.supermarket.model.specialoffer.FiveForAmount;
import dojo.supermarket.model.specialoffer.PercentDiscount;
import dojo.supermarket.model.specialoffer.ThreeForTwo;
import dojo.supermarket.model.specialoffer.TwoForAmount;
import org.approvaltests.Approvals;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class SupermarketTest {
    private SupermarketCatalog catalog;
    private Teller teller;
    private ShoppingCart theCart;
    private Product toothbrush;
    private Product rice;
    private Product apples;
    private Product cherryTomatoes;

    @BeforeEach
    public void setUp() {
        catalog = new FakeCatalog();
        teller = new Teller(catalog);
        theCart = new ShoppingCart();

        toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 2.99);
        apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        cherryTomatoes = new Product("cherry tomato box", ProductUnit.Each);
        catalog.addProduct(cherryTomatoes, 0.69);

    }

    @Test
    void an_empty_shopping_cart_should_cost_nothing() {
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void one_normal_item() {
        theCart.addItem(toothbrush);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void two_normal_items() {
        theCart.addItem(toothbrush);
        theCart.addItem(rice);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void buy_two_get_one_free() {
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(new ThreeForTwo(), toothbrush, catalog.getUnitPrice(toothbrush));
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void buy_two_get_one_free_but_insufficient_in_basket() {
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(new ThreeForTwo(), toothbrush, catalog.getUnitPrice(toothbrush));
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }
    @Test
    void buy_five_get_one_free() {
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        theCart.addItem(toothbrush);
        teller.addSpecialOffer(new ThreeForTwo(), toothbrush, catalog.getUnitPrice(toothbrush));
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void loose_weight_product() {
        theCart.addItemQuantity(apples, .5);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void percent_discount() {
        theCart.addItem(rice);
        teller.addSpecialOffer(new PercentDiscount(), rice, 10.0);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void xForY_discount() {
        theCart.addItem(cherryTomatoes);
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(new TwoForAmount(), cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @Test
    void xForY_discount_with_insufficient_in_basket() {
        theCart.addItem(cherryTomatoes);
        teller.addSpecialOffer(new TwoForAmount(), cherryTomatoes,.99);
        Receipt receipt = teller.checksOutArticlesFrom(theCart);
        Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
    }

    @ParameterizedTest
    @MethodSource("quantityAndSpecialOfferAmount")
    void FiveForY_discount_withQuantityAndAmount(double quantity, double amount) {
        try (NamedEnvironment en = NamerFactory.withParameters(quantity)) {
            theCart.addItemQuantity(apples, quantity);
            teller.addSpecialOffer(new FiveForAmount(), apples,amount);
            Receipt receipt = teller.checksOutArticlesFrom(theCart);
            Approvals.verify(new ReceiptPrinter(40).printReceipt(receipt));
        }
    }

    private static Stream<Arguments> quantityAndSpecialOfferAmount() {
        return Stream.of(
                arguments(4, 8.99),
                arguments(5, 6.99),
                arguments(6, 5.99),
                arguments(16, 7.99)
        );
    }
}
