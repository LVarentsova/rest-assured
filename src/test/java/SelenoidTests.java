import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {
    // make request to https://selenoid.autotests.cloud/status
    // total 20

    @Test
    void checkTotal20() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total",is(20));
    }

    @Test
    void checkTotal20WithoutGiven() {
        get("https://selenoid.autotests.cloud/status")
            .then()
            .body("total",is(20));
    }

    @Test
    void checkTotal20WithResponseAndBadPractice() {
        String response =
        get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().response().asString();

        System.out.println(response);

        // bad practice

        assertEquals("{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{\"android\":{\"8.1\":{}},\"chrome\":{\"90.0\":{},\"91.0\":{}},\"firefox\":{\"88.0\":{},\"89.0\":{}},\"opera\":{\"76.0\":{},\"77.0\":{}}}}", response);
    }

    @Test
    void checkTotal20WithTalkAboutResponse() {
        Response response =
                get("https://selenoid.autotests.cloud/status")
                        .then()
                        .extract().response();

        System.out.println(response.asString());
        System.out.println(response.path("total") + "");
        System.out.println(response.path("browsers.chrome") + "");
    }

    @Test
    void checkTotal20WithAssertJ() {
        Integer response = get("https://selenoid.autotests.cloud/status")
                .then()
                .extract()
                .path("total");

        System.out.println(response);

        assertThat(response).isEqualTo(20);
    }

    @Test
    void checkWdStatus401() {
        get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(401);
    }

    @Test
    void checkWdStatus200() {
        get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    void checkWdStatus200WithAuth() {
        given()
                .auth().basic("user1","1234")
                .get("https://user1:1234@selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200)
                .body("value.ready", is(true));
    }

}
