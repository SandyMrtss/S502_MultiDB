package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class PlayerNotFoundException extends EntityNotFoundException {
    public PlayerNotFoundException(){
        super("No player was found with the chosen id");
    }
}
