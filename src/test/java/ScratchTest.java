import org.testng.annotations.Test;
import utils.ApiUtils;
import utils.UriUtils;

public class ScratchTest {

    @Test
    public static void generateAuthorizationUrl() {
        System.out.println("authorization url: " + UriUtils.getAuthorizationUrlUri());
    }

    @Test
    public static void getAccessToken() {
        ApiUtils.getAccessToken();
    }

    @Test
    public static void temp1() {
//        StringUtils.idContainsNewsletterName("Culture", "culture_previews");
    }
}
