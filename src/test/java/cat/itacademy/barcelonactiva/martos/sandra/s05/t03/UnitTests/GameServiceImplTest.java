package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.UnitTests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.GameRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl.GameServiceImpl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.utils.RandomDiceGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameService gameService = new GameServiceImpl(gameRepository);

    private PlayerEntity playerEntity;
    private List<GameEntity> listGameEntity;

    @BeforeEach
    void setup(){
        playerEntity = new PlayerEntity("sandy");
        GameEntity game1 = new GameEntity(playerEntity, 1,3);
        GameEntity game2 = new GameEntity(playerEntity, 1,6);
        GameEntity game3 = new GameEntity(playerEntity, 4,2);
        GameEntity game4 = new GameEntity(playerEntity, 2,2);

        listGameEntity = new ArrayList<>();
        listGameEntity.add(game1);
        listGameEntity.add(game2);
        listGameEntity.add(game3);
        listGameEntity.add(game4);
    }

    @Test
    @DisplayName("GameDTO to GameEntity works")
    void testGameDTOToEntity(){
        GameDTO game = new GameDTO(RandomDiceGenerator.newRandomDice(), RandomDiceGenerator.newRandomDice());
        GameEntity gameEntity = gameService.gameDTOToEntity(playerEntity, game);
        assertTrue(game.getDice1() == gameEntity.getDice1()
                && game.getDice2() == gameEntity.getDice2()
                && gameEntity.getPlayerEntity().equals(playerEntity));
    }

    @Test
    @DisplayName("GameEntity to GameDTO works")
    void testGameEntityToDTO(){
        GameEntity gameEntity = new GameEntity(playerEntity, 3,2);
        GameDTO  gameDTO = gameService.gameEntityToDTO(gameEntity);
        assertTrue(gameDTO.getDice1() == gameEntity.getDice1()
                && gameDTO.getDice2() == gameEntity.getDice2()
                && !gameDTO.isWin());
    }

    @Test
    @DisplayName("New game creation")
    void testAddGame(){
        GameDTO newGame = gameService.addGame(playerEntity);
        listGameEntity.add(gameService.gameDTOToEntity(playerEntity, newGame));

        Mockito.when(gameRepository.findByPlayerEntity(playerEntity)).thenReturn(listGameEntity);

        List<GameDTO> actualList = gameService.getAllGames(playerEntity);
    }

    @Test
    @DisplayName("List all games for player")
    void testGetAllGames(){
        Mockito.when(gameRepository.findByPlayerEntity(playerEntity))
                .thenReturn(listGameEntity);

        List<GameDTO> actual = gameService.getAllGames(playerEntity);

        List<GameEntity> actualEntity = new ArrayList<>();
        actual.forEach(g-> actualEntity.add(gameService.gameDTOToEntity(playerEntity, g)));

        assertTrue(new ReflectionEquals(listGameEntity).matches(actualEntity));
    }

    @Test
    @DisplayName("All games from player deleted successfully")
    void testDeleteAllGames(){
        gameService.deleteAllGames(playerEntity);
        Mockito.verify(gameRepository).deleteByPlayerEntity(playerEntity);
    }

    @Test
    @DisplayName("Gets proper success rate")
    void testGetSuccessRate(){
        double expected = 25.0;
        Mockito.when(gameRepository.calcAverageByPlayerEntity(playerEntity))
                .thenReturn(expected);
        double actual = gameService.getSuccessRate(playerEntity);

        assertEquals(Math.round(expected), Math.round(actual));
    }
}
