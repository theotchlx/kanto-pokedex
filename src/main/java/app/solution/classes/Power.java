package app.solution.classes;
public class Power {
    private String powerName;

    public PokemonType getDamageType() {
        return damageType;
    }

    public void setDamageType(PokemonType damageType) {
        this.damageType = damageType;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    private PokemonType damageType;
    private Integer damage;

    public Power() {
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }
}
