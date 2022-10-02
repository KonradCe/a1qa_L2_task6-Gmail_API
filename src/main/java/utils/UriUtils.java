package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class UriUtils {
    private static final ISettingsFile configData = new JsonSettingsFile("ConfigData.json");
    private static final ISettingsFile credentials = new JsonSettingsFile("ClientSecret.json");
    private static final Logger logger = Logger.getInstance();

    public static URI getAuthorizationUrlUri() {
        URI resultUri = null;

        try {
            resultUri = new URIBuilder(configData.getValue("/authorizationCodeRequestUrl").toString())
                    .addParameter("client_id", credentials.getValue("/installed/client_id").toString())
                    .addParameter("redirect_uri", credentials.getList("/installed/redirect_uris").get(0))
                    .addParameter("response_type", "code")
                    .addParameter("scope", "https://mail.google.com/")
                    .addParameter("access_type", "online")
//                    .addParameter("prompt", "select_account")
                    .build();
            logger.debug("getAuthorizationUrlUri: " + resultUri.toString());
        } catch (URISyntaxException e) {
            logger.error("Error building URI to get authorization URL: " + e.getMessage());
        }
        return resultUri;
    }
}
