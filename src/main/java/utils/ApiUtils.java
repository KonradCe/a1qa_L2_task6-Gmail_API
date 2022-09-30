package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class ApiUtils {
    private static final Logger logger = Logger.getInstance();
    private static final ISettingsFile testData = new JsonSettingsFile("TestData.json");
}
