package app.utils.models;

//import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pokemon implements Comparable<Pokemon> {
    //@JsonProperty("pokemonName")
    private String pokemonName;
    private PokemonType type;
    private int lifePoints;
    private Set<Power> powers;  // Defined as Set and not HashSet for flexibility and maintainability

    public Pokemon() {
    }

    public Pokemon(String pokemonName, PokemonType type, int lifePoints, HashSet<Power> powers) {
        this.pokemonName = pokemonName;
        this.type = type;
        this.lifePoints = lifePoints;
        this.powers = powers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return lifePoints == pokemon.lifePoints && pokemonName.equals(pokemon.pokemonName) && type == pokemon.type && powers.equals(pokemon.powers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pokemonName, type, lifePoints, powers);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "pokemonName='" + pokemonName + '\'' +
                ", type=" + type +
                ", lifePoints=" + lifePoints +
                ", powers=" + powers +
                '}';
    }

    @Override
    public int compareTo(Pokemon pokemon) {
        return this.pokemonName.compareTo(pokemon.pokemonName);
    }

    // Getters and setters
    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public Set<Power> getPowers() {
        return powers;
    }

    public void setPowers(Set<Power> powers) {
        this.powers = powers;
    }
}
