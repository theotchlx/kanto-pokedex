package app.utils.models;

import app.utils.exceptions.InvalidPokemonException;
import app.utils.exceptions.PokedexEmptyException;
import app.utils.exceptions.PokemonAlreadyExistsException;
import app.utils.exceptions.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class Pokedex {
    private static final Set<Pokemon> pokedex = new HashSet<>();  // HashSet to store the Pokemon. Defined as Set for flexibility and maintainability
    private static final Logger log = LoggerFactory.getLogger(Pokedex.class);

    public static void addPokemon(Pokemon pokemon) throws PokemonAlreadyExistsException, InvalidPokemonException {
        if (pokedex.contains(pokemon)) {
            throw new PokemonAlreadyExistsException("Pokemon already exists in the pokedex");
        }
        if (pokemon == null) {
            throw new InvalidPokemonException("Pokemon is null, cannot add it to the pokedex.");
        }
        pokedex.add(pokemon);
    }

    public static void removePokemon(Pokemon pokemon) throws PokedexEmptyException, PokemonNotFoundException {
        if (pokedex.isEmpty()) {
            throw new PokedexEmptyException("Pokedex is empty, cannot remove any Pokemon.");
        }
        if (!pokedex.contains(pokemon)) {
            throw new PokemonNotFoundException("Pokemon not found in the pokedex.");
        }
        pokedex.remove(pokemon);
    }

    public static Pokemon searchPokemonByName(String name) throws PokemonNotFoundException, PokedexEmptyException {
        // If the pokedex does not contain this pokemon, throw InvalidQueryParameterException
        if (pokedex.isEmpty()) {
            throw new PokedexEmptyException("Pokedex is empty, cannot search for any Pokemon.");
        }
        for (Pokemon pokemon : pokedex) {
            log.info("Looking for pokemon: " + name);
            log.info("Current pokemon: " + pokemon.getPokemonName());
            if (pokemon.getPokemonName().equals(name)) {
                return pokemon;
            }
        }
        throw new PokemonNotFoundException("Pokemon not found in the pokedex.");
    }

    public static Set<Pokemon> getPokedex() {
        return pokedex;
    }

    public static void clearPokedex() {
        pokedex.clear();
    }

    public static boolean contains(Pokemon pokemon) {
        /*
            I made this method if I want to access the pokedex from another class.
            In this class, I just use pokedex.contains() instead.
        */
        return pokedex.contains(pokemon);
    }
}
