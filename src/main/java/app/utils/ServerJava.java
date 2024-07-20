package app.utils;

import app.utils.exceptions.InvalidPokemonException;
import app.utils.exceptions.PokemonAlreadyExistsException;
import app.utils.exceptions.PokemonNotFoundException;
import app.utils.models.Pokedex;
import app.utils.models.Pokemon;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerJava {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(ServerJava.class);

    public ServerJava() {
        // TODO : compléter les endpoints pour que les tests passent au vert !
        app = Javalin.create()
                .get("/api/status", ctx -> {
                    logger.debug("Status handler triggered", ctx);
                    ctx.status(200);
                })
                .post("/api/create", ctx -> {
                    /* User Story 1:
                        Si le pokémon existe déjà ou que le json est incomplet ou invalide, une erreur 400 est renvoyée par le serveur.
                        En cas de réussite, le code 200 est envoyé par le serveur.
                    */
                    try {
                        Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);

                        Pokedex.addPokemon(pokemon);

                        System.out.println(Pokedex.getPokedex());  // Logging the pokedex to the console for development purposes

                        logger.debug("Pokemon created: " + pokemon);
                        ctx.status(200).result("Pokemon created successfully");
                    } catch (PokemonAlreadyExistsException e) {
                        logger.error("Pokemon already exists in pokedex: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // Pokemon already exists in the pokedex
                    } catch (InvalidPokemonException e) {
                        logger.error("Pokemon not valid for creation: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // Pokemon is null, cannot add it to the pokedex.
                    }
                })
                .get("/api/searchByName", ctx -> {
                    /* User Story 2:
                        Si aucun pokémon ne correspond à cette chaine, une liste vide est renvoyée avec le code 200.
                        Si le paramètre est invalide (par exemple de mauvais type), le serveur répond avec le code d'erreur 400.
                    */
                    try {
                        //String nameToSearch = ctx.queryParam("name");
                        Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);
                        String nameToSearch = pokemon.getPokemonName();

                        System.out.println(nameToSearch);  // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

                        Pokedex.searchPokemonByName(nameToSearch);

                        ctx.status(200).result("Pokemon found in pokedex");
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while searching pokemon by name: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());
                    }
                })
                .post("/api/modify", ctx -> {
                    //String typeToSearch = ctx.queryParam("type");
                })
                .get("/api/searchByType", ctx -> {
                    //ModifyPokemonRequest request = ctx.bodyAsClass(ModifyPokemonRequest.class);
                })
                .delete("/api/delete", ctx -> {
                    /* BONUS:
                        Deleting a Pokémon
                    */
                    try {
                        //String nameToDelete = ctx.queryParam("name");
                        Pokemon receivedPokemon = ctx.bodyAsClass(Pokemon.class);
                        String nameToDelete = receivedPokemon.getPokemonName();

                        Pokemon pokemon = Pokedex.searchPokemonByName(nameToDelete);
                        Pokedex.removePokemon(pokemon);

                        System.out.println(Pokedex.getPokedex());  // Logging the pokedex to the console for development purposes

                        ctx.status(200).result("Pokemon deleted from pokedex");
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while deleting pokemon by name: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());
                    }
                })
        ;
    }

    public Javalin javalinApp() {
        return app;
    }
}
