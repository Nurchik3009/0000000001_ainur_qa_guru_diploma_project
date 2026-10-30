package tests.ui;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Epic("UI тесты")
@Feature("Каталог, корзина и оформление заказа")
@Owner("Ainur Kaliakbarova")
@Severity(SeverityLevel.CRITICAL)
@Link(name = "SauceDemo System", url = "https://www.saucedemo.com/")
public class SauceDemoCatalogAndCartUiTests extends TestBase {

    LoginPage loginPage = new LoginPage();
    ProductsPage productsPage = new ProductsPage();
    CartPage cartPage = new CartPage();
    CheckoutPage checkoutPage = new CheckoutPage();

    @Test
    @AllureId("49809")
    @Issue("REF-29")
    @Story("Каталог и сортировка")
    @DisplayName("Сортировка товаров по цене (low to high)")
    @Tag("ui")
    void shouldSortProductsByPrice() {
        step("Авторизоваться на сайте", () -> {
            open("/");
            $("#user-name").setValue("standard_user");
            $("#password").setValue("secret_sauce");
            $("#login-button").click();
        });

        step("Выбрать сортировку по возрастанию цены", () -> {
            $(".product_sort_container").selectOptionByValue("lohi");
        });

        step("Проверить, что первый товар — самый дешевый ($7.99)", () -> {
            $$(".inventory_item_price").first().shouldHave(text("$7.99"));
        });
    }

    @Test
    @AllureId("49807")
    @Issue("REF-29")
    @Story("Корзина")
    @DisplayName("Добавление товара в корзину и проверка бейджа")
    @Tag("ui")
    void shouldAddToCart() {
        loginPage.login("standard_user", "secret_sauce");

        productsPage.checkTitle("Products")
                .addBackpackToCart()
                .checkCartBadge("1");
    }

    @Test
    @AllureId("49812")
    @Issue("REF-29")
    @Story("Оформление заказа")
    @DisplayName("Полный цикл покупки товара (Checkout Flow)")
    @Tag("ui")
    void successFullCheckoutFlowTest() {
        loginPage.login("standard_user", "secret_sauce");

        productsPage.checkTitle("Products")
                .addBackpackToCart()
                .checkCartBadge("1")
                .openCart();

        cartPage.checkItemInCart("Sauce Labs Backpack")
                .proceedToCheckout();

        checkoutPage.fillCustomerInfo("Ainur", "Kaliakbarova", "050000")
                .clickContinue()
                .clickFinish()
                .checkSuccessMessage("Thank you for your order!");
    }
}