package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UsernameAlreadyUsedException extends DataIntegrityViolationException {
    public UsernameAlreadyUsedException(){
        super("Username already used");
    }
}
