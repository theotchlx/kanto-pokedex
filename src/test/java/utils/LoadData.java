package utils;

import io.javalin.testtools.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.UrlPaths.CREATE_URL;

public class LoadData {

    public static void loadDataIntoApp(HttpClient httpClient) throws IOException {
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_pikachu.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_dracaufeu.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_bulbizarre.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_herbizarre.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get("src/test/resources/datasets/create_evoli.json"))));

    }
}
