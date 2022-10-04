package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.path.json.JsonPath;

import javax.naming.AuthenticationException;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GmailUtils {
    private static ISettingsFile configData = new JsonSettingsFile("ConfigData.json");
    private static ISettingsFile apiConfig = new JsonSettingsFile("GmailApiConfig.json");

    public static String getUrlToConfirmSubscription() throws AuthenticationException {
        String messageBody = getBodyMessageOfId(getIdOfUnreadEuronewsEmails().get(0));
        return StringUtils.extractUrlFromHtml(messageBody);
    }

    private static String getBodyMessageOfId(String mailId) {
        String encodedString = given()
                .header("Host", apiConfig.getValue("/headerHostValue").toString())
                .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                .get(apiConfig.getValue("/allMessagesEndPoint").toString() + mailId)
                .jsonPath()
                .getString("payload.parts[0].body.data");

        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    private static List<String> getIdOfUnreadEuronewsEmails() throws AuthenticationException {
        JsonPath response = given()
                .header("Host", apiConfig.getValue("/headerHostValue").toString())
                .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                .get(apiConfig.getValue("/messagesQueryEndPoint").toString())
                .jsonPath();
        if (response.get("resultSizeEstimate") == null) {
            throw new AuthenticationException("Please provide correct values of authorization code and access token to the ConfigData.json file");
        }
        return response.get("messages.id");
    }

    public static Integer markAllEmailsAsRead() throws AuthenticationException {
        List<String> unreadMessagesIds = getIdOfUnreadEuronewsEmails();
        if (unreadMessagesIds != null) {
            for (String id : unreadMessagesIds) {
                markMessageAsRead(id);
            }
            return unreadMessagesIds.size();
        }
        return 0;
    }

    private static void markMessageAsRead(String id) {
        given()
                .header("Host", apiConfig.getValue("/headerHostValue").toString())
                .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                .body(apiConfig.getValue("/markMessagesAsReadRequestBody").toString())
                .post(apiConfig.getValue("/allMessagesEndPoint").toString() + id + "/modify");
    }

    public static boolean isNewMailExists() throws InterruptedException {
        for (int i = 0; i < Integer.parseInt(configData.getValue("/numberOfSecondsToWaitForNewMail").toString()); i++) {
            int numberOfUnreadEmails = given()
                    .header("Host", apiConfig.getValue("/headerHostValue").toString())
                    .header("Authorization", "Bearer " + configData.getValue("/accessToken").toString())
                    .get(apiConfig.getValue("/messagesQueryEndPoint").toString())
                    .jsonPath()
                    .getInt("resultSizeEstimate");

            if (numberOfUnreadEmails > 0) {
                return true;
            }
            Thread.sleep(1000);
        }
        return false;
    }
}
