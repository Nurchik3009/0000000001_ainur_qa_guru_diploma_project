package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutPage {

    private final SelenideElement firstNameInput = $("#first-name");
    private final SelenideElement lastNameInput = $("#last-name");
    private final SelenideElement postalCodeInput = $("#postal-code");
    private final SelenideElement continueButton = $("#continue");
    private final SelenideElement finishButton = $("#finish");
    private final SelenideElement successHeader = $(".complete-header");

    @Step("Заполнить данные покупателя: имя={firstName}, фамилия={lastName}, индекс={postalCode}")
    public CheckoutPage fillCustomerInfo(String firstName, String lastName, String postalCode) {
        firstNameInput.setValue(firstName);
        lastNameInput.setValue(lastName);
        postalCodeInput.setValue(postalCode);
        return this;
    }

    @Step("Нажать кнопку Continue")
    public CheckoutPage clickContinue() {
        continueButton.click();
        return this;
    }

    @Step("Нажать кнопку Finish для завершения заказа")
    public CheckoutPage clickFinish() {
        finishButton.click();
        return this;
    }

    @Step("Проверить сообщение об успешном оформлении заказа: {expectedMessage}")
    public void checkSuccessMessage(String expectedMessage) {
        successHeader.shouldBe(visible).shouldHave(text(expectedMessage));
    }
}