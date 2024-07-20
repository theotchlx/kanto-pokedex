import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.UrlPaths.CREATE_URL;

class UseCase1IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have


    @Test
    void test_create_pikachu() throws IOException {

        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_pikachu.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_dracaufeu() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_dracaufeu.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_bulbizarre() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_bulbizarre.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_KO_bulbizarre_already_in() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_bulbizarre.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(400);
        });
    }

    @Test
    void test_create_KO_invalid_json() throws IOException {
        String invalidLifepoints = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_invalid_wrong_lifepoints.json")));
        String invalidType = new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_invalid_wrong_type.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, invalidLifepoints).code()).isEqualTo(400);
            assertThat(client.post(CREATE_URL, invalidType).code()).isEqualTo(400);
        });

    }


}
