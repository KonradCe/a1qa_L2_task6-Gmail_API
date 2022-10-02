package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class StringUtils {

    public static String extractUrlFromHtml(String messageBody) {
        Document doc = Jsoup.parse(messageBody);
        return doc.select("a").attr("href");
    }
}
