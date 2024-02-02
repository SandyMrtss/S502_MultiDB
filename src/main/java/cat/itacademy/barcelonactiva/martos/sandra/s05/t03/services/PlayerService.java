package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;

import java.util.List;

public interface PlayerService {
    public void addPlayer(PlayerDTORequest playerDTORequest);
    public void updatePlayer(Integer id, PlayerDTORequest playerDTORequest);
    public PlayerEntity getPlayer(Integer id);
    public List<PlayerEntity> getAllPlayers();
    public GameDTO addGame(Integer idPlayer);
    public List<GameDTO> getAllGames(Integer idPlayer);
    public void deleteAllGames(Integer idPlayer);
    public double getAverageSuccessRate();
    public List<PlayerDTO> getAllSuccessRate();
    public PlayerDTO getWinner();
    public PlayerDTO getLoser();

    //public PlayerDTO playerToDTO(PlayerEntity playerEntity);
    public PlayerEntity playerDTOToEntity(PlayerDTORequest playerDTORequest);
}