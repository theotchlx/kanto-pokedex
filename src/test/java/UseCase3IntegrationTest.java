import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.LoadData.loadDataIntoApp;
import static utils.UrlPaths.SEARCH_BY_NAME_URL;
import static utils.UrlPaths.SEARCH_BY_TYPE_URL;

class UseCase3IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_search_grass() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_TYPE_URL + "GRASS");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Herbizarre").contains("Bulbizarre");
        });
    }

    @Test
    void test_search_fire() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_TYPE_URL + "FIRE");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Dracaufeu");
        });
    }

    @Test
    void test_search_inexisting_type() {
        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.get(SEARCH_BY_TYPE_URL + "SUN");
            assertThat(response.code()).isEqualTo(400);
            assert response.body() != null;
            assertThat(response.body().string()).isEqualTo("");
        });
    }
}
