package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProductsPage {

    private final SelenideElement title = $(".title");
    private final SelenideElement addToCartButton = $("#add-to-cart-sauce-labs-backpack");
    private final SelenideElement cartBadge = $(".shopping_cart_badge");
    private final SelenideElement cartLink = $(".shopping_cart_link");

    @Step("Проверить, что открыта страница каталога с заголовком: {expectedTitle}")
    public ProductsPage checkTitle(String expectedTitle) {
        title.shouldBe(visible).shouldHave(text(expectedTitle));
        return this;
    }

    @Step("Добавить товар 'Sauce Labs Backpack' в корзину")
    public ProductsPage addBackpackToCart() {
        addToCartButton.click();
        return this;
    }

    @Step("Проверить, что счетчик корзины равен {expectedCount}")
    public ProductsPage checkCartBadge(String expectedCount) {
        cartBadge.shouldBe(visible).shouldHave(text(expectedCount));
        return this;
    }

    @Step("Перейти в корзину")
    public CartPage openCart() {
        cartLink.click();
        return new CartPage();
    }
}