package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.utils;

public class RandomDiceGenerator {
    private final static int MIN = 1;
    private final static int MAX = 6;

    public static int newRandomDice(){
        return (int) (Math.random()*(MAX - MIN + 1)+ MIN);
    }
}
