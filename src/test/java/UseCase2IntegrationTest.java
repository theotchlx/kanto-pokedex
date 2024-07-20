import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.LoadData.loadDataIntoApp;
import static utils.ResponseParser.isJsonEquals;
import static utils.ResponseParser.parseResponse;
import static utils.UrlPaths.SEARCH_BY_NAME_URL;

class UseCase2IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_search_pikachu() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_NAME_URL + "Pikachu");

            // THEN
            assertThat(response.code()).isEqualTo(200);
            JSONArray jsonResponse = parseResponse(response);
            assertThat(jsonResponse.length()).isEqualTo(1);
            assertThat(jsonResponse.getJSONObject(0).toString()).is(isJsonEquals(
                    new String(Files.readAllBytes(Paths.get("resources/datasets/create_pikachu.json")))));
        });
    }

    @Test
    void test_search_dracaufeu() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_NAME_URL + "Dracaufeu");

            // THEN
            assertThat(response.code()).isEqualTo(200);
            JSONArray jsonResponse = parseResponse(response);
            assertThat(jsonResponse.length()).isEqualTo(1);
            assertThat(jsonResponse.getJSONObject(0).toString()).is(isJsonEquals(
                    new String(Files.readAllBytes(Paths.get("resources/datasets/create_dracaufeu.json")))));
        });
    }

    @Test
    void test_search_bizarre() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_NAME_URL + "bizarre");

            // THEN
            JSONArray jsonResponse = parseResponse(response);
            assertThat(jsonResponse.length()).isEqualTo(2);
            Path herbizarrePath = Paths.get("resources/datasets/create_herbizarre.json");
            Path bulbizarrePath = Paths.get("resources/datasets/create_bulbizarre.json");
            assertThat(jsonResponse.getJSONObject(0).toString())
                    .is(anyOf(
                            isJsonEquals(new String(Files.readAllBytes(bulbizarrePath))),
                            isJsonEquals(new String(Files.readAllBytes(herbizarrePath)))));
            assertThat(jsonResponse.getJSONObject(1).toString())
                    .is(anyOf(
                            isJsonEquals(new String(Files.readAllBytes(bulbizarrePath))),
                            isJsonEquals(new String(Files.readAllBytes(herbizarrePath)))));
        });
    }

    @Test
    void test_search_unexisting() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_NAME_URL + "Ronflex");
            JSONArray jsonResponse = parseResponse(response);
            assertThat(response.code()).isEqualTo(200);
            assertThat(jsonResponse.length()).isZero();
        });
    }

    @Test
    void test_search_case_insensitive() {
        JavalinTest.test(app, (server, client) -> {
            // GIVEN
            loadDataIntoApp(client);

            // WHEN
            Response response = client.get(SEARCH_BY_NAME_URL + "pIkAcHu");

            // THEN
            assertThat(response.code()).isEqualTo(200);
            JSONArray jsonResponse = parseResponse(response);
            assertThat(jsonResponse.length()).isEqualTo(1);
            assertThat(jsonResponse.getJSONObject(0).toString()).is(isJsonEquals(
                    new String(Files.readAllBytes(Paths.get("resources/datasets/create_pikachu.json")))));
        });
    }
}
