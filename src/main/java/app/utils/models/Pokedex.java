package app.utils.models;

import app.utils.exceptions.InvalidPokemonException;
import app.utils.exceptions.PokedexEmptyException;
import app.utils.exceptions.PokemonAlreadyExistsException;
import app.utils.exceptions.PokemonNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pokedex {
    private Set<Pokemon> pokedex;  // HashSet to store the Pokemon. Defined as Set for flexibility and maintainability

    public Pokedex() {
        this.pokedex = new HashSet<>();
    }

    public Pokedex(HashSet<Pokemon> pokedex) {
        this.pokedex = pokedex;
    }

    public void addPokemon(Pokemon pokemon) throws PokemonAlreadyExistsException, InvalidPokemonException {
        if (pokedex.contains(pokemon)) {
            throw new PokemonAlreadyExistsException("Pokemon already exists in the pokedex");
        }
        if (pokemon == null) {
            throw new InvalidPokemonException("Pokemon is null, cannot add it to the pokedex.");
        }
        pokedex.add(pokemon);
    }

    public void removePokemon(Pokemon pokemon) throws PokedexEmptyException, PokemonNotFoundException {
        if (pokedex.isEmpty()) {
            throw new PokedexEmptyException("Pokedex is empty, cannot remove any Pokemon.");
        }
        if (!pokedex.contains(pokemon)) {
            throw new PokemonNotFoundException("Pokemon not found in the pokedex.");
        }
        pokedex.remove(pokemon);
    }

    public ArrayList<Pokemon> searchPokemonByName(String name) {
        ArrayList<Pokemon> foundPokemons = new ArrayList<>();
        for (Pokemon pokemon : pokedex) {
            if (pokemon.getPokemonName().toLowerCase().contains(name.toLowerCase())) {
                foundPokemons.add(pokemon);
            }
        }
        return foundPokemons;
        // Returns an empty list if the pokedex is empty or does not contain the searched Pokemons.
    }

    public Set<Pokemon> getPokedex() {
        return pokedex;
    }

    public void clearPokedex() {
        pokedex.clear();
    }

    public boolean contains(Pokemon pokemon) {
        /*
            I made this method if I want to access the pokedex from another class.
            In this class, I just use pokedex.contains() instead.
        */
        return pokedex.contains(pokemon);
    }
}
