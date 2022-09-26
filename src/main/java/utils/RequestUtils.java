package utils;

import org.asynchttpclient.uri.Uri;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestUtils {

    public static void getAuthorizationUrl() {
        HttpRequest.BodyPublisher bodyPublisher = new HttpRequest.BodyPublishers.ofString(
                "code=4/0ARtbsJq4lWUaCYlq1tIItqIBkiWVdbcWO2upt-U00-KlcegYnLjJQM4AZ20jtNd0nXmY4g" +
                        "client_id=" + credentials.getValue("/installed/client_id).toString()")
        )
        HttpResponse<String> response1 = ApiUtils.getRequest(UriUtils.getAuthorizationUrlUri());
        HttpResponse<String> response2 = ApiUtils.postRequest(
                UriUtils.getAccessTokenForAuthCode(""),
                null);
        System.out.println(response2.statusCode());
        System.out.println(response2.body());
    }
}
