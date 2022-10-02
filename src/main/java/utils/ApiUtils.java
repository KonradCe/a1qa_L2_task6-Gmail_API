package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import static io.restassured.RestAssured.given;

public class ApiUtils {
    private static final Logger logger = Logger.getInstance();
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
    private static final ISettingsFile configData = new JsonSettingsFile("ConfigData.json");
    private static final ISettingsFile clientSecret = new JsonSettingsFile("ClientSecret.json");

    public static void getAccessToken() {
        String requestBody =
                "code=" + configData.getValue("/authorizationCode").toString() + "&" +
                        "client_id=" + clientSecret.getValue("/installed/client_id").toString() + "&" +
                        "client_secret=" + clientSecret.getValue("/installed/client_secret").toString() + "&" +
                        "redirect_uri=" + clientSecret.getList("/installed/redirect_uris").get(0) + "&" +
                        "grant_type=authorization_code";
        given().
                header("Host", "oauth2.googleapis.com").
                header("Content-Type", "application/x-www-form-urlencoded").
                body(requestBody)
                .post("https://oauth2.googleapis.com/token").then().log().body();
    }
}

// https://gmail.googleapis.com/gmail/v1/users/me/messages?q=euronews label: unread
