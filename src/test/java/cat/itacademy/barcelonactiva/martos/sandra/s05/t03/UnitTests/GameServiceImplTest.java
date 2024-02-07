package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.UnitTests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.Game;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
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
    private List<Game> listGame;
    private GameHistory gameHistory;

    @BeforeEach
    void setup(){
        playerEntity = new PlayerEntity("sandy");
        Game game1 = new Game(1,3);
        Game game2 = new Game(1,6);
        Game game3 = new Game(4,2);
        Game game4 = new Game(2,2);

        listGame = new ArrayList<>();
        listGame.add(game1);
        listGame.add(game2);
        listGame.add(game3);
        listGame.add(game4);
        gameHistory = new GameHistory(1);
        gameHistory.setAllGames(listGame);
        gameHistory.setSuccessRate(25.0);
    }

    @Test
    @DisplayName("Game to GameDTO")
    void testGameDTOToEntity(){
        Game game = new Game(RandomDiceGenerator.newRandomDice(), RandomDiceGenerator.newRandomDice());
        GameDTO gameDTO = gameService.gameToDTO(game);
        boolean expected = game.getDice1() + game.getDice2() == 7;
        assertTrue(game.getDice1() == gameDTO.getDice1()
                && game.getDice2() == gameDTO.getDice2()
                && gameDTO.isWin() == expected);

    }
    @Test
    @DisplayName("New game creation")
    void testAddGame(){
        Mockito.when(gameRepository.findByPlayerId(1)).thenReturn(gameHistory);

        GameDTO newGame = gameService.addGame(1);
        listGame.add(new Game(newGame.getDice1(), newGame.getDice2()));

        List<GameDTO> actualList = gameService.getAllGames(1);
        assertTrue(new ReflectionEquals(listGame).matches(actualList));
   }

    @Test
    @DisplayName("Test new game updates successRate")
    void  testUpdateGameHistory(){
        Mockito.when(gameRepository.findByPlayerId(1)).thenReturn(gameHistory);
        GameDTO newGame = gameService.addGame(1);
        double expected;
        if(newGame.isWin()) {
            expected = 40.0;
        }
        else {
            expected = 20.0;
        }
        assertEquals(gameHistory.getSuccessRate(), expected);
    }

    @Test
    @DisplayName("List all games for player")
    void testGetAllGames(){
        Mockito.when(gameRepository.findByPlayerId(1))
                .thenReturn(gameHistory);

        List<GameDTO> actual = gameService.getAllGames(1);

        List<Game> expected = gameHistory.getAllGames();

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    @DisplayName("All games from player deleted successfully")
    void testDeleteAllGames(){
        GameHistory newHistory = new GameHistory(2);
        List<Game> newList = new ArrayList<>();
        newList.add(new Game(1,3));
        newHistory.setAllGames(newList);

        Mockito.when(gameRepository.findByPlayerId(2)).thenReturn(newHistory);
        gameService.deleteAllGames(2);
        List<GameDTO> actual = gameService.getAllGames(2);

        assertTrue(new ReflectionEquals(new ArrayList<>()).matches(actual));
    }

    @Test
    @DisplayName("Get success rate works")
    void testGetSuccessRate(){
        Mockito.when(gameRepository.findByPlayerId(1)).thenReturn(gameHistory);

        double actual = gameService.getSuccessRate(1);
        assertEquals(gameHistory.getSuccessRate(), actual);
    }
}
