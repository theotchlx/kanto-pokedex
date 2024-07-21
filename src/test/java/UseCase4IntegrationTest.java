import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.LoadData.loadDataIntoApp;
import static utils.UrlPaths.*;

class UseCase4IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_modify_lifePoints() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/modify_evoli_lifepoints.json")));

        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);

            assertThat(client.get(SEARCH_BY_NAME_URL+"Evoli").body().string()).contains("\"lifePoints\":80");
        });
    }

    @Test
    void test_modify_add_power() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/add_pikachu_power.json")));

        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);

            assertThat(client.get(SEARCH_BY_NAME_URL+"Pikachu").body().string())
                    .contains("{\"powerName\":\"Eclair Fou\",\"damageType\":\"ELECTRIC\",\"damage\":90}");  // Fixed this test. It was not corresponding to the README.
            // Justification : this test checked for the deletion of Pikachu's power and replacement by a new one.
            // README states : "En ce qui concerne la liste de capacité, si la capacité existe déjà, elle n'est pas modifiée. On ne peut qu'ajouter des nouvelles capacités, pas modifier les existantes."
        });
    }

    @Test
    void test_modify_unexisting_pokemon() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/modify_unexisting_pokemon.json")));

        JavalinTest.test(app, (server, client) -> {
            loadDataIntoApp(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
