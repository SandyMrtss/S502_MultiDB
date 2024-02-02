package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class PlayerDTO {
    private String username;

    private Double successRate;

    public PlayerDTO(String username, Double successRate) {
        this.username = Objects.requireNonNullElse(username, "ANONYMOUS");
        this.successRate = successRate;
    }

}
