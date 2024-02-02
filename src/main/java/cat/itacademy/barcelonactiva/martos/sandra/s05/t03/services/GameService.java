package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.Game;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;

import java.util.List;

public interface GameService {
    public void addGameHistory(Integer id);
    public GameDTO addGame(Integer id);
    public List<GameDTO> getAllGames(Integer id);
    public void deleteAllGames(Integer id);
    public Double getSuccessRate(Integer id);
    public GameDTO gameToDTO(Game game);
}
