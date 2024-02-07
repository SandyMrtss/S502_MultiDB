package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.unittests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.utils.RandomDiceGenerator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {
    @RepeatedTest(20)
    @DisplayName("Test random dice generator")
    void testNewRandomDice(){
        int number = RandomDiceGenerator.newRandomDice();
        assertTrue(number > 0 && number < 7);
    }

}
