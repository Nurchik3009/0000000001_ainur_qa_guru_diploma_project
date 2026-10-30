package tests.ui;

import com.codeborne.selenide.ClickOptions;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;

@Epic("UI тесты")
@Feature("Авторизация и сессии")
@Owner("Ainur Kaliakbarova")
@Severity(SeverityLevel.BLOCKER)
public class SauceDemoLoginUiTests extends TestBase {

    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Успешный вход")
    @DisplayName("Успешный вход валидного пользователя (standard_user)")
    @Tag("ui")
    void successfulLoginTest() {
        loginPage.openPage()
                .setUsername("standard_user")
                .setPassword("secret_sauce")
                .clickLoginButton();

        step("Проверить успешный вход и заголовок каталога", () -> {
            $(".title").shouldBe(visible).shouldHave(text("Products"));
        });
    }

    @Test
    @Story("Негативные кейсы входа")
    @DisplayName("Ошибка при входе заблокированного пользователя (locked_out_user)")
    @Tag("ui")
    void lockedOutUserLoginTest() {
        loginPage.openPage()
                .setUsername("locked_out_user")
                .setPassword("secret_sauce")
                .clickLoginButton();

        loginPage.checkErrorMessage("Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    @Story("Негативные кейсы входа")
    @DisplayName("Ошибка при входе с неверным паролем")
    @Tag("ui")
    void shouldShowErrorOnInvalidPassword() {
        step("Открыть страницу входа", () -> open("/"));

        step("Ввести неверные данные", () -> {
            $("#user-name").setValue("standard_user");
            $("#password").setValue("wrong_password");
            $("#login-button").click();
        });

        step("Проверить появление сообщения об ошибке", () -> {
            $("[data-test='error']").shouldBe(visible).shouldHave(text("Username and password do not match any user in this service"));
        });
    }

    @Test
    @Story("Завершение сессии")
    @DisplayName("Разлогин из системы")
    @Tag("ui")
    void shouldLogoutSuccessfully() {
        step("Авторизоваться на сайте", () -> {
            open("/");
            $("#user-name").setValue("standard_user");
            $("#password").setValue("secret_sauce");
            $("#login-button").click();
        });

        step("Открыть боковое меню и нажать Logout", () -> {
            $("#react-burger-menu-btn").click();
            sleep(500);
            $("#logout_sidebar_link").click(ClickOptions.usingJavaScript());
        });

        step("Проверить возврат на форму входа", () -> {
            $("#login-button").shouldBe(visible);
        });
    }
}