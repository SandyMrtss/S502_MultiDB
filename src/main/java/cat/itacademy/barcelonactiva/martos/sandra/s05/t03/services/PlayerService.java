package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;

import java.util.List;

public interface PlayerService {
    void addPlayer(PlayerDTORequest playerDTORequest);
    void updatePlayer(Integer id, PlayerDTORequest playerDTORequest);
    PlayerEntity getPlayer(Integer id);
    List<PlayerEntity> getAllPlayers();
    GameDTO playGame(Integer idPlayer);
    List<GameDTO> getAllGames(Integer idPlayer);
    void deleteAllGames(Integer idPlayer);
    List<PlayerDTO> getAllSuccessRate();
    double getAverageSuccessRate();
    PlayerDTO getWinner();
    PlayerDTO getLoser();
    PlayerEntity playerDTOToEntity(PlayerDTORequest playerDTORequest);
}