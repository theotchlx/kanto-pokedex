package app.utils;

import app.utils.dtos.ModifyPokemonRequest;
import app.utils.exceptions.*;
import app.utils.models.Pokedex;
import app.utils.models.Pokemon;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
                    String nameToSearch = ctx.queryParam("name");  // Get the name of the Pokemons to search for from the ?name="..." query parameter

                    ArrayList<Pokemon> pokemons = pokedex.searchPokemonByName(nameToSearch);  // Search for the Pokemons of this name in the pokedex

                    ObjectMapper objectMapper = new ObjectMapper();
                    String pokemonJson = objectMapper.writeValueAsString(pokemons);  // Convert ArrayList<Pokemon> object to JSON string

                    logger.debug("Pokemons searched for: " + nameToSearch);

                    ctx.contentType("application/json");  // Set response content type to JSON
                    ctx.status(200).result(pokemonJson);  // Return the found Pokemons ArrayList as a JSON string

                    // There are no exceptions specific to this method. It will return an empty list if the search is unsuccessful.
                    // And the name parameter cannot be invalid as long as it is a String (this is checked by the global exception handlers down below).
                })
                .get("/api/searchByType", ctx -> {
                    /* User Story 3:
                        Si aucun pokémon ne correspond à ce type, une liste vide est renvoyée avec le code 200.
                        Si le type recherché n'est pas dans la liste de type possible, le serveur renvoie une requête vide avec le code d'erreur 400.
                    */
                    try {
                        String typeToSearch = ctx.queryParam("type");  // Get the type of the Pokemons to search for from the ?type="..." query parameter

                        ArrayList<Pokemon> pokemons = pokedex.searchPokemonByType(typeToSearch);  // Search for the Pokemons of this type in the pokedex

                        ObjectMapper objectMapper = new ObjectMapper();
                        String pokemonJson = objectMapper.writeValueAsString(pokemons);  // Convert ArrayList<Pokemon> object to JSON string

                        logger.debug("Pokemons searched for: " + typeToSearch);

                        ctx.contentType("application/json");  // Set response content type to JSON
                        ctx.status(200).result(pokemonJson);  // Return the found Pokemons ArrayList as a JSON string
                    } catch (TypeDoesNotExistException e) {
                        logger.error("Element searched for does not exist in query parameter: " + e.getMessage());
                        ctx.status(400);  // No message (as per US3 integration test)
                    }
                })
                .post("/api/modify", ctx -> {
                    /* User Story 4:
                        Si la modification est effectuée, le serveur répond avec le code 200.
                        Si le pokémon n'existe pas, le serveur répond avec le code 404.
                        Si le json est invalide, le serveur répond avec le code 400.
                    */
                    try {
                        ModifyPokemonRequest request = ctx.bodyAsClass(ModifyPokemonRequest.class);

                        Pokemon updatedPokemon = new Pokemon();  // Create a new Pokemon object to store the updated Pokemon data

                        updatedPokemon.setType(request.getType());
                        updatedPokemon.setLifePoints(request.getLifePoints());
                        updatedPokemon.setPowers(request.getPowers());

                        pokedex.modifyPokemon(request.getPokemonName(), updatedPokemon);  // Modify the Pokemon in the pokedex with the updated pokemon

                        logger.debug("Pokemon modified: " + request.getPokemonName());

                        ctx.status(200).result("Pokemon modified successfully.");
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while modifying pokemon: " + e.getMessage());
                        ctx.status(404).result(e.getMessage());  // "Pokemon not found in the pokedex."
                    } catch (PokedexEmptyException e) {
                        logger.error("Exception encountered while modifying pokemon: " + e.getMessage());
                        ctx.status(404).result(e.getMessage());  // "Pokedex is empty, cannot modify any Pokemon."
                    }
                })
                .delete("/api/delete", ctx -> {
                    /* PERSONAL BONUS:
                        Deleting a Pokémon from the pokédex
                        Why did I implement this? Because pokédexes have limited storage, cheap ones only have 500Mb of disk space.
                        (also because I wanted some tests to run in a series and they could only run one by one if I didn't delete some pokémons)
                    */
                    try {
                        String nameToDelete = ctx.queryParam("name");  // Get the name of the Pokemon to delete from the ?name="..." query parameter

                        ArrayList<Pokemon> pokemons = pokedex.searchPokemonByName(nameToDelete);
                        for (Pokemon pokemon : pokemons) {
                            pokedex.removePokemon(pokemon);  // Remove each found Pokemon from the pokedex
                        }

                        ObjectMapper objectMapper = new ObjectMapper();
                        String pokemonJson = objectMapper.writeValueAsString(pokemons);  // Convert ArrayList<Pokemon> object to JSON string

                        logger.debug("Pokemon deleted: " + nameToDelete);

                        ctx.contentType("application/json");  // Set response content type to JSON
                        ctx.status(200).result(pokemonJson);  // Return the deleted Pokemons ArrayList as a JSON string
                    } catch (PokemonNotFoundException e) {
                        logger.error("Exception encountered while deleting pokemon: " + e.getMessage());
                        ctx.status(404).result(e.getMessage());  // "Pokemon not found in the pokedex."
                    } catch (PokedexEmptyException e) {
                        logger.error("Exception encountered while deleting pokemon: " + e.getMessage());
                        ctx.status(404).result(e.getMessage());  // "Pokedex is empty, cannot remove any Pokemon."
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
