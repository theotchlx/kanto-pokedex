package app.utils;

import app.utils.exceptions.InvalidPokemonException;
import app.utils.exceptions.PokedexEmptyException;
import app.utils.exceptions.PokemonAlreadyExistsException;
import app.utils.exceptions.PokemonNotFoundException;
import app.utils.models.Pokedex;
import app.utils.models.Pokemon;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerJava {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(ServerJava.class);

    public ServerJava() {
        // TODO : compléter les endpoints pour que les tests passent au vert !

        // Instanciation of a new Pokedex to store the Pokemon
        Pokedex pokedex = new Pokedex();

        app = Javalin.create()
                .get("/api/status", ctx -> {
                    logger.debug("Status handler triggered" + ctx);
                    ctx.status(200);
                })
                .post("/api/create", ctx -> {
                    /* User Story 1:
                        Si le pokémon existe déjà ou que le json est incomplet ou invalide, une erreur 400 est renvoyée par le serveur.
                        En cas de réussite, le code 200 est envoyé par le serveur.
                    */
                    try {
                        Pokemon pokemon = ctx.bodyAsClass(Pokemon.class);  // Parse the JSON body into a Pokemon object

                        pokedex.addPokemon(pokemon);  // Add the Pokemon to the pokedex

                        logger.debug("Pokemon created: " + pokemon);
                        ctx.status(200).result("Pokemon created successfully.");
                    } catch (PokemonAlreadyExistsException e) {
                        logger.error("Pokemon already exists in pokedex: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // Pokemon already exists in the pokedex
                    } catch (InvalidPokemonException e) {
                        logger.error("Pokemon not valid for creation: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // "Pokemon is null, cannot add it to the pokedex."
                    }
                    // --> Invalid JSON errors (invalid pokemon type, invalid JSON format) are caught by the global exception handler down below.
                })
                .get("/api/searchByName", ctx -> {
                    /* User Story 2:
                        Si aucun pokémon ne correspond à cette chaine, une liste vide est renvoyée avec le code 200.
                        Si le paramètre est invalide (par exemple de mauvais type), le serveur répond avec le code d'erreur 400.
                    */
                    try {
                        String nameToSearch = ctx.queryParam("name");  // Get the name of the Pokemon to delete from the ?name="..." query parameter

                        Pokemon pokemon = pokedex.searchPokemonByName(nameToSearch);  // Search for the Pokemon by name in the pokedex

                        ctx.status(200).result("Pokemon found in pokedex:" + pokemon);  // Return the found Pokemon (call to .toString())
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while searching pokemon by name: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // "Pokemon not found in the pokedex."
                    } catch (PokedexEmptyException e) {
                        logger.error("Exception encountered while searching pokemon by name: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // "Pokedex is empty, cannot search for any Pokemon."
                    }
                })
                .post("/api/modify", ctx -> {
                    //String typeToSearch = ctx.queryParam("type");
                })
                .get("/api/searchByType", ctx -> {
                    //ModifyPokemonRequest request = ctx.bodyAsClass(ModifyPokemonRequest.class);
                })
                .delete("/api/delete", ctx -> {
                    /* PERSONAL BONUS:
                        Deleting a Pokémon from the pokédex
                        Why did I implement this? Because pokédexes have limited storage, cheap ones only have 500Mb of disk space.
                        (also because I wanted some tests to run in a series and they could only run one by one if I didn't delete some pokémons)
                    */
                    try {
                        String nameToDelete = ctx.queryParam("name");  // Get the name of the Pokemon to delete from the ?name="..." query parameter

                        Pokemon pokemon = pokedex.searchPokemonByName(nameToDelete);
                        pokedex.removePokemon(pokemon);

                        ctx.status(200).result("Pokemon deleted from pokedex.");
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while deleting pokemon: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // "Pokemon not found in the pokedex."
                    } catch (PokedexEmptyException e) {
                        logger.error("Exception encountered while deleting pokemon: " + e.getMessage());
                        ctx.status(400).result(e.getMessage());  // "Pokedex is empty, cannot remove any Pokemon."
                    }
                })
        ;

        // Global exception handler for JsonMappingException for when the JSON is not properly formatted
        app.exception(JsonMappingException.class, (e, ctx) -> {
            logger.error("Invalid JSON format: " + e.getMessage());
            ctx.status(400).result("Invalid JSON format.");
        });

        // Global exception handler for InvalidFormatException, for when the JSON has an invalid value like invalid pokemon type
        app.exception(InvalidFormatException.class, (e, ctx) -> {
            logger.error("Invalid value in JSON body: " + e.getMessage());
            ctx.status(400).result("Invalid value in JSON body.");
        });

        // Global exception handler for generic exceptions, to return an error message to the client in case of an unexpected exception
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Server cannot handle request, unexpected error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            ctx.status(500).result("Server cannot handle request.");
        });
    }

    public Javalin javalinApp() {
        return app;
    }
}
