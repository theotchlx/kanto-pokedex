package app.utils.exceptions;

public class PokemonAlreadyExistsException extends Exception {
    public PokemonAlreadyExistsException(String message) {
        super(message);
    }
}
