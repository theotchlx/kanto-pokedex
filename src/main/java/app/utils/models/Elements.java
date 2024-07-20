package app.utils.models;

public enum Elements {
        NEUTRAL,
        FIRE,
        WATER,
        GRASS,
        ELECTRIC,
        ICE,
        FIGHTING,
        POISON,
        GROUND;

    public static boolean isValidElement(String type) {
        for (Elements element : Elements.values()) {
            if (element.toString().equals(type.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}

