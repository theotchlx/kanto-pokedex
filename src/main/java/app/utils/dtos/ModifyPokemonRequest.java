package app.utils.dtos;

import app.utils.models.Elements;
import app.utils.models.Power;

import java.util.Set;

public class ModifyPokemonRequest {  // DTO for the /api/modify Pokémon in Pokédex request
    // To have a clear separation of concerns between the Pokemon object and the request.
    private String pokemonName;
    private Elements type;
    private int lifePoints;  // Value defaults to 0 if not set.
    private Set<Power> powers;

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
