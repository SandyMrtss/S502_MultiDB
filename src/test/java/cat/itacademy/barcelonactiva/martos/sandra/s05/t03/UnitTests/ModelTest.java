package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.UnitTests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelTest {

    @Test
    @DisplayName("GameDTO wins is properly instanced when winning")
    void testGameWin() {
        GameDTO gameDTO = new GameDTO(3,4);
        assertTrue(gameDTO.isWin());
    }

    @Test
    @DisplayName("GameDTO wins is properly instanced when losing")
    void testGameLose() {
        GameDTO gameDTO = new GameDTO(3,3);
        assertFalse(gameDTO.isWin());
    }
}
