package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private int dice1;
    private int dice2;
    private boolean win;

    public GameDTO(int dice1, int dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        if(dice1 + dice2 == 7){
            this.win = true;
        }
        else {
            this.win = false;
        }
    }
}
