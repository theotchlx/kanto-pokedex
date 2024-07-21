package app.utils.models;

import app.utils.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pokedex {
    private final Set<Pokemon> pokedex;  // HashSet to store the Pokemon. Defined as Set for flexibility and maintainability

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

    public ArrayList<Pokemon> searchPokemonByType(String type) throws TypeDoesNotExistException {
        if (!Elements.isValidElement(type)) {
            throw new TypeDoesNotExistException("Pokemon type does not exist.");
        }
        ArrayList<Pokemon> foundPokemons = new ArrayList<>();
        for (Pokemon pokemon : pokedex) {
            if (pokemon.getType().toString().equals(type.toUpperCase())) {
                foundPokemons.add(pokemon);
            }
        }
        return foundPokemons;
        // Returns an empty list if the pokedex is empty or does not contain the searched Pokemons.
    }

    public void modifyPokemon(String pokemonName, Pokemon updatedPokemon) throws PokedexEmptyException, PokemonNotFoundException {
        if (pokedex.isEmpty()) {
            throw new PokedexEmptyException("Pokedex is empty, cannot modify any Pokemon.");
        }
        if (searchPokemonByName(pokemonName).isEmpty()) {
            throw new PokemonNotFoundException("Pokemon not found in the pokedex.");
        }

        // Find the existing Pokemon in the pokedex, which will be modified
        Pokemon existingPokemon = null;
        for (Pokemon pokemon : pokedex) {
            if (pokemon.getPokemonName().equals(pokemonName)) {  // Only the first Pokemon of the name will be modified. See Pokemon.equals() comments for more details on the thinking behind this implementation.
                existingPokemon = pokemon;
                break;
            }
        }

        // Modify the attributes of the existing pokemon, if they are set
        if (updatedPokemon.getType() != null) {
            existingPokemon.setType(updatedPokemon.getType());
        }
        if (updatedPokemon.getLifePoints() > 0) {  // Life points default to 0 if not set - makes sense as if a pokemon were to die, it would get deleted, not modified.
            existingPokemon.setLifePoints(updatedPokemon.getLifePoints());
        }
        if (updatedPokemon.getPowers() != null) {
            Set<Power> existingPowers = existingPokemon.getPowers();
            for (Power power : updatedPokemon.getPowers()) {
                if (!existingPowers.contains(power)) {
                    existingPowers.add(power);
                }
            }
        }
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
