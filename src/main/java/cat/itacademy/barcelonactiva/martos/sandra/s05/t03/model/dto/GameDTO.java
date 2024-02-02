package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto;

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

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

}
