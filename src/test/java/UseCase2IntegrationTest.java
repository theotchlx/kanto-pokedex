import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.testtools.HttpClient;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.LoadData.loadDataIntoApp;
import static utils.UrlPaths.CREATE_URL;
import static utils.UrlPaths.SEARCH_BY_NAME_URL;

class UseCase2IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_search_pikachu() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_NAME_URL + "Pikachu");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Pikachu");
        });
    }

    @Test
    void test_search_dracaufeu() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_NAME_URL + "Dracaufeu");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Dracaufeu");
        });
    }

    @Test
    void test_search_bizarre() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_NAME_URL + "bizarre");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Bulbizarre").contains("Herbizarre");
        });
    }

    @Test
    void test_search_unexisting() throws IOException {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_NAME_URL + "Ronflex");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
        });
    }
}
