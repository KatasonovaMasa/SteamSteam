package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.User2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.SteamSteps;

import java.util.Locale;

import static help.CustomApiListener2.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteamTest extends TestBase {
    SteamSteps steamSteps = new SteamSteps();

    @Tag("steam")
    @Test
    @Feature("Автотесты")
    @Story("Раздел Игр")
    @Owner("Катасонова Мария")
    @DisplayName("Открытие раздела игр 'Выживание'")
    void openSurvivalGames() {
        steamSteps.openSteam();
        steamSteps.openShop();
        steamSteps.openCategories();
        steamSteps.openSectionSurvivalGames();
        steamSteps.successSurvivalGames();
    }

    @Tag("steam")
    @Test
    @Feature("Автотесты")
    @Story("Разделы Активность сообщества")
    @Owner("Катасонова Мария")
    @DisplayName("Рекомендации в Активности сообщества")
    void checkCommunityActive() {
        steamSteps.openSteam();
        steamSteps.openCommunity();
        steamSteps.successActiveCommunity();
    }

    @Tag("steam")
    @Test
    @Feature("Автотесты")
    @Story("Раздел Игр")
    @Owner("Катасонова Мария")
    @DisplayName("Кнопка поиска игр")
    void searchJob() {
        steamSteps.openSteam();
        steamSteps.setSearch();
        steamSteps.clickSearch();
        steamSteps.successSearchJob();
    }

    @Tag("steam")
    @Test
    @Feature("Автотесты")
    @Story("Раздел Игр")
    @Owner("Катасонова Мария")
    @DisplayName("Кнопка поиска игр")
    void searchJobApi() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        User2 data = given()
                .filter(withCustomTemplates())
                .log().uri()
                .contentType(ContentType.JSON)
                .queryParam("start", "50")
                .queryParam("term", "Cuphead")
                .queryParam("supportedlang", "russian")
                .queryParam("infinite", "1")
                .spec(Specs2.request)
                .when()
                .get("/?query")
                .then()
                .spec(Specs2.responseSpec)
                .log().body()
                .extract().as(User2.class);
        assertEquals(1, data.getSuccess());
        assertEquals( 17, data.getTotal_count());
    }

    @Tag("steam")
    @Test
    @Feature("Автотесты")
    @Story("Корзина игр")
    @Owner("Катасонова Мария")
    @DisplayName("Добавление игры в корзину")
    void potentialBuyGames() {
        steamSteps.openSteam();
        steamSteps.openGames();
        steamSteps.addGameToCart();
        steamSteps.successBasketGame();
    }

    @Tag("external")
    @Test
    @Feature("Автотесты")
    @Story("Корзина игр")
    @Owner("Катасонова Мария")
    @DisplayName("Удалить игру из корзины")
    void deleteGameCart() {
        steamSteps.openSteam();
        steamSteps.addGameCart();
        steamSteps.deleteGameCart();
        steamSteps.successCartEmpty();
    }


}