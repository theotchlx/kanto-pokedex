import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.LoadData.loadDataIntoApp;
import static utils.ResponseParser.isJsonEquals;
import static utils.ResponseParser.parseResponse;
import static utils.UrlPaths.SEARCH_BY_TYPE_URL;

class UseCase3IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_search_grass() {

        // !! READ ME !! This test to be failing some of the time, even though the server always returns the correct response.
        // I was not able to diagnose exactly why this happens.

        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_TYPE_URL + "GRASS");
            // THEN
            assertThat(response.code()).isEqualTo(200);

            // WHEN
            JSONArray jsonResponse = parseResponse(response);
            // THEN
            assertThat(jsonResponse.length()).isEqualTo(2);
            Path herbizarrePath = Paths.get("resources/datasets/create_herbizarre.json");
            Path bulbizarrePath = Paths.get("resources/datasets/create_bulbizarre.json");
            Assertions.assertThat(jsonResponse.getJSONObject(0).toString())
                    .is(anyOf(
                            isJsonEquals(new String(Files.readAllBytes(bulbizarrePath))),
                            isJsonEquals(new String(Files.readAllBytes(herbizarrePath)))));
            Assertions.assertThat(jsonResponse.getJSONObject(1).toString())
                    .is(anyOf(
                            isJsonEquals(new String(Files.readAllBytes(bulbizarrePath))),
                            isJsonEquals(new String(Files.readAllBytes(herbizarrePath)))));
        });
    }

    @Test
    void test_search_fire() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_TYPE_URL + "FIRE");
            assertThat(response.code()).isEqualTo(200);
            JSONArray jsonResponse = parseResponse(response);

            // THEN
            assertThat(jsonResponse.length()).isEqualTo(1);
            assertThat(jsonResponse.getJSONObject(0).toString())
                    .is(isJsonEquals(new String(Files.readAllBytes(Paths.get("resources/datasets/create_dracaufeu.json")))));
        });
    }

    @Test
    void test_search_case_insensitive() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_TYPE_URL + "fIrE");
            assertThat(response.code()).isEqualTo(200);
            JSONArray jsonResponse = parseResponse(response);

            // THEN
            assertThat(jsonResponse.length()).isEqualTo(1);
            assertThat(jsonResponse.getJSONObject(0).toString())
                    .is(isJsonEquals(new String(Files.readAllBytes(Paths.get("resources/datasets/create_dracaufeu.json")))));
        });
    }

    @Test
    void test_search_inexisting_type() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_TYPE_URL + "SUN");

            // THEN
            assertThat(response.code()).isEqualTo(400);
            assert response.body() != null;
            assertThat(response.body().string()).isNullOrEmpty();
        });
    }
}
