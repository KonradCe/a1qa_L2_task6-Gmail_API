package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.path.json.JsonPath;

import java.util.Base64;

import static io.restassured.RestAssured.given;

public class GmailUtils {
    private static final ISettingsFile configData = new JsonSettingsFile("ConfigData.json");

    public static String getUrlToConfirmSubscription() {

        String messageBody = getBodyMessageOfId(getIdOfUnreadEmail());
        return StringUtils.extractUrlFromHtml(messageBody);
    }

    private static String getBodyMessageOfId(String mailId) {
        String response = given()
                .header("Host", "gmail.googleapis.com")
                .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                .get("https://gmail.googleapis.com/gmail/v1/users/me/messages/" + mailId)
                .asString();
        JsonPath jsonPath = new JsonPath(response);
        String encodedString = jsonPath.getString("payload.parts[0].body.data");

        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private static String getIdOfUnreadEmail() {
        String response = given()
                .header("Host", "gmail.googleapis.com")
                .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                .get("https://gmail.googleapis.com/gmail/v1/users/me/messages?q=euronews label: unread")
                .asString();

        return new JsonPath(response).getString("messages[0].id");
    }
}
