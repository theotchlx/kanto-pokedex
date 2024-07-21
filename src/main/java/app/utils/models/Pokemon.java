package app.utils.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Pokemon implements Comparable<Pokemon> {
    private String pokemonName;
    private Elements type;
    private int lifePoints;
    private Set<Power> powers;  // Defined as Set and not HashSet for flexibility and maintainability

    public Pokemon() {
        this.powers = new HashSet<>();
    }

    public Pokemon(String pokemonName, Elements type, int lifePoints, Set<Power> powers) {
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
    // I was unsure if a Pokémon is unique by all its attributes or just by its name. The 4th user story makes me believe that a Pokémon is unique only by its name... But a Pokédex can contain multiple pokémons of the same species ??
    // Due to this, modifying a pokemon only modifies the first pokémon of its name.
    @Override
    public int hashCode() {
        return Objects.hash(pokemonName, type, lifePoints, powers);
    }

    @Override
    public String toString() {
        return "Pokemon {" +
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

    public Elements getType() {
        return type;
    }

    public void setType(Elements type) {
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
