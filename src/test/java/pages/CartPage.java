package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {

    private final SelenideElement cartItemName = $(".inventory_item_name");
    private final SelenideElement checkoutButton = $("#checkout");

    @Step("Проверить, что в корзине присутствует товар: {expectedItemName}")
    public CartPage checkItemInCart(String expectedItemName) {
        cartItemName.shouldBe(visible).shouldHave(text(expectedItemName));
        return this;
    }

    @Step("Нажать кнопку Checkout (Оформить заказ)")
    public CheckoutPage proceedToCheckout() {
        checkoutButton.click();
        return new CheckoutPage();
    }
}