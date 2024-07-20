package app.utils;

import app.App;
import app.solution.classes.Pokemon;
import app.solution.classes.PokemonType;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerJava {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(ServerJava.class);

    public Map<String, Pokemon> pokedex = new HashMap<>();

    public ServerJava() {
        // TODO : compléter les endpoints pour que les tests passent au vert !
        app = Javalin.create()
                .get("/api/status", ctx -> {
                    logger.debug("Status handler triggered", ctx);
                    ctx.status(200);
                })
                .post("/api/create", ctx -> {
                    //Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);
                })
                .get("/api/searchByName", ctx -> {
                    //String nameToSearch = ctx.queryParam("name");
                })
                .post("/api/modify", ctx -> {
                    //String typeToSearch = ctx.queryParam("type");
                })
                .get("/api/searchByType", ctx -> {
                    //ModifyPokemonRequest request = ctx.bodyAsClass(ModifyPokemonRequest.class);
                })
        ;
    }

    public Javalin javalinApp() {
        return app;
    }
}
