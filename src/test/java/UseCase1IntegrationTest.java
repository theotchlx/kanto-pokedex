import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.UrlPaths.CREATE_URL;
//import static utils.UrlPaths.DELETE_URL;

class UseCase1IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have


    // ###################################################################################################################### //
    //                                                    ##### NOTE #####                                                    //
    //                                                                                                                        //
    // I had to change the path of the datasets files because the Test Resources Root directory was not properly recognized.  //
    // Paths changed from "src/test/resources/datasets" to "resources/datasets".                                              //
    //                                                                                                                        //
    // I also left some comment, modified some tests.                                                                         //
    //                                                                                                                        //
    //                                                  ##### END NOTE #####                                                  //
    // ###################################################################################################################### //


    @Test
    void test_create_pikachu() throws IOException {

        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/create_pikachu.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_dracaufeu() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/create_dracaufeu.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_bulbizarre() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/create_bulbizarre.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
            /*
            The following comment were added when my Pokédex class was still static and are not relevant anymore
            I left them here because they show an interesting process/logic.
            I should have used an afterEach() method to delete the pokemon after the test instead of an assert.

            //assertThat(client.delete(DELETE_URL + "Bulbizarre").code()).isEqualTo(200);  // I added this line to delete the pokemon after the test
            // So that the test can be run multiple times without failing, and the next test can run right after without failing either.

            */
        });
    }

    @Test
    void test_create_KO_bulbizarre_already_in() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get("resources/datasets/create_bulbizarre.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(400);
        });
    }

    @Test
    void test_create_KO_invalid_json() throws IOException {
        String invalidLifepoints = new String(Files.readAllBytes(Paths.get("resources/datasets/create_invalid_wrong_lifepoints.json")));
        String invalidType = new String(Files.readAllBytes(Paths.get("resources/datasets/create_invalid_wrong_type.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, invalidLifepoints).code()).isEqualTo(400);
            assertThat(client.post(CREATE_URL, invalidType).code()).isEqualTo(400);
        });

    }


}
