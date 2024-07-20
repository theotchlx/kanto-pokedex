package app.solution.classes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Pokemon {
    private String pokemonName;
    private PokemonType type;
    private Integer lifePoints;
    private Set<Power> powers;

    public Pokemon() {
    }

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

    public Integer getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(Integer lifepoints) {
        this.lifePoints = lifepoints;
    }

    public Set<Power> getPowers() {
        return powers;
    }

    public void setPowers(Set<Power> powers) {
        this.powers = powers;
    }


    public void updateFromRequest(Pokemon pokemon) {
        if (pokemon.getLifePoints() != null) this.setLifePoints(pokemon.getLifePoints());
        if (pokemon.getType() != null) this.setType(pokemon.getType());
        if (pokemon.getPowers() != null && !pokemon.getPowers().isEmpty()) this.powers.addAll(
                pokemon.getPowers().stream().filter(
                        power -> this.powers.contains(power)).collect(Collectors.toSet()));

    }
}
