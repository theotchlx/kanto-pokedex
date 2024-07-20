package app.utils.models;

import java.util.Objects;

public class Power {
    private String powerName;
    private Elements damageType;
    private int damage;

    public Power() {
    }

    public Power(String powerName, Elements damageType, int damage) {
        this.powerName = powerName;
        this.damageType = damageType;
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Power power = (Power) o;
        return damage == power.damage && powerName.equals(power.powerName) && damageType == power.damageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(powerName, damageType, damage);
    }

    // Getters and setters
    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Elements getDamageType() {
        return damageType;
    }

    public void setDamageType(Elements damageType) {
        this.damageType = damageType;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
