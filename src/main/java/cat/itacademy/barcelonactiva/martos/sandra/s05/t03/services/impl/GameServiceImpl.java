package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.Game;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.GameRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.utils.RandomDiceGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    private Game newGame() {
        return new Game(RandomDiceGenerator.newRandomDice(), RandomDiceGenerator.newRandomDice());
    }

    @Override
    public void addGameHistory(Integer id){
        gameRepository.save(new GameHistory(id));
    }

    @Override
    public GameDTO addGame(Integer id){
        Game game = newGame();
        GameHistory gameHistory= gameRepository.findByPlayerId(id).get(0);
        updateGameHistory(gameHistory, game);
        return gameToDTO(game);
    }

    private void updateGameHistory(GameHistory gameHistory, Game game){
        double isWinNewGame;
        GameDTO newGame = gameToDTO(game);

        if(newGame.isWin()){
            isWinNewGame = 1.0d;
        }
        else{
            isWinNewGame = 0.0d;
        }
        if(gameHistory.getSuccessRate() == null){
            gameHistory.setSuccessRate(isWinNewGame*100);
        }
        else{
            double rate = gameHistory.getSuccessRate();
            int gamesPlayed = gameHistory.getAllGames().size();
            int gamesWin = (int) (Math.round(rate/100*gamesPlayed));
            gameHistory.setSuccessRate((gamesWin + isWinNewGame)*100/(gamesPlayed + 1));
        }
        gameHistory.addGame(game);
        gameRepository.save(gameHistory);
    }
    @Override
    public List<GameDTO> getAllGames(Integer id) {
        GameHistory gameEntity = getGameHistoryById(id);
        List<Game> gameList = gameEntity.getAllGames();
        List<GameDTO> gameDTOList = new ArrayList<>();
        gameList.stream().toList().forEach(g-> gameDTOList.add((gameToDTO(g))));
        return gameDTOList;
    }

    @Override
    public void deleteAllGames(Integer id) {
        GameHistory gameHistory = getGameHistoryById(id);
        gameHistory.setAllGames(new ArrayList<>());
        gameRepository.save(gameHistory);
    }

    @Override
    public Double getSuccessRate(Integer id){
        GameHistory gameHistory = getGameHistoryById(id);
        return gameHistory.getSuccessRate();
    }

    private GameHistory getGameHistoryById(Integer id){
        try{
            return gameRepository.findByPlayerId(id).get(0);
        }catch (NoSuchElementException ex){
            throw new PlayerNotFoundException();
        }
    }
    @Override
    public GameDTO gameToDTO(Game game){
        return new GameDTO(game.getDice1(), game.getDice2());
    }
}
