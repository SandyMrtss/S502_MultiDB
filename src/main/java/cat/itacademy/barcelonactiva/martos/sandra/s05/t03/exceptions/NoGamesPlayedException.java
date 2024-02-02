package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions;

import java.util.NoSuchElementException;

public class NoGamesPlayedException extends NoSuchElementException {
    public NoGamesPlayedException(){
        super("No games have been played yet");
    }
}
