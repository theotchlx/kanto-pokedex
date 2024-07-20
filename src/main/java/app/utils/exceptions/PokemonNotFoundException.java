package app.utils.exceptions;

public class PokemonNotFoundException extends Exception {
    public PokemonNotFoundException(String message) {
        super(message);
    }
}