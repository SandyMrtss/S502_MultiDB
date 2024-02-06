package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.Game;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    void addGameHistory(Integer id);
    GameDTO addGame(Integer id);
    List<GameDTO> getAllGames(Integer id);
    void deleteAllGames(Integer id);
    Double getSuccessRate(Integer id);
    GameDTO gameToDTO(Game game);
}
