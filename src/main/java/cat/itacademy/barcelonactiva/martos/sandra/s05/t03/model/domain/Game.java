package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain;

import lombok.*;

import java.util.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private int dice1;
    private int dice2;
    private Date creationDate;

    public Game(int dice1, int dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.creationDate = new Date(System.currentTimeMillis());
    }
}
