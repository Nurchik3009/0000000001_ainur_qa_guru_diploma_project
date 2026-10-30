package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("#login-button");
    private final SelenideElement errorMessage = $("[data-test='error']");

    @Step("Открыть страницу авторизации SauceDemo")
    public LoginPage openPage() {
        open("/");
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Нажать кнопку логина")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Авторизоваться пользователем: {username}")
    public void login(String username, String password) {
        openPage();
        setUsername(username);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Проверить текст ошибки: {expectedError}")
    public void checkErrorMessage(String expectedError) {
        errorMessage.shouldBe(visible).shouldHave(com.codeborne.selenide.Condition.text(expectedError));
    }
}