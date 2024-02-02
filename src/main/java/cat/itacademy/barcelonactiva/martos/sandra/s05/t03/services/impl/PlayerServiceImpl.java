package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.impl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.NoGamesPlayedException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.exceptions.PlayerNotFoundException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.PlayerEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.GameService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {
    PlayerRepository playerRepository;
    GameService gameService;
    public PlayerServiceImpl(PlayerRepository playerRepository, GameService gameService){
        this.playerRepository = playerRepository;
        this.gameService = gameService;
    }
    @Override
    public void addPlayer(PlayerDTORequest playerDTORequest) {
        PlayerEntity playerEntity = playerDTOToEntity(playerDTORequest);
        playerRepository.save(playerEntity);
        gameService.addGameHistory(playerEntity.getId());
    }

    @Override
    public void updatePlayer(Integer id, PlayerDTORequest playerDTORequest) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if(playerEntity.isEmpty()){
            throw new EntityNotFoundException();
        }
        playerEntity.get().setUsername(playerDTORequest.getUsername());
        playerRepository.save(playerEntity.get());
    }

    @Override
    public PlayerEntity getPlayer(Integer id) {
        Optional<PlayerEntity> playerEntity = playerRepository.findById(id);
        if(playerEntity.isEmpty()){
            throw new PlayerNotFoundException();
        }
        return playerEntity.get();
    }

    @Override
    public GameDTO addGame(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        return gameService.addGame(idPlayer);
    }

    @Override
    public List<GameDTO> getAllGames(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        return gameService.getAllGames(idPlayer);
    }

    @Override
    public void deleteAllGames(Integer idPlayer) {
        PlayerEntity playerEntity = getPlayer(idPlayer);
        gameService.deleteAllGames(idPlayer);
    }

    @Override
    public List<PlayerEntity> getAllPlayers(){
        return playerRepository.findAll();
    }
    @Override
    public List<PlayerDTO> getAllSuccessRate() {
        List<PlayerEntity> playerEntityList = getAllPlayers();
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        playerEntityList.forEach(p-> playerDTOList.add(new PlayerDTO(p.getUsername(), gameService.getSuccessRate(p.getId()))));

        return playerDTOList;
    }
    @Override
    public double getAverageSuccessRate() {
        List<PlayerDTO> playerDTOList = getAllSuccessRate();

        return playerDTOList.stream()
                .filter(p-> p.getSuccessRate() != null)
                .mapToDouble(PlayerDTO::getSuccessRate).average()
                .orElseThrow(NoGamesPlayedException::new);
    }

    @Override
    public PlayerDTO getWinner() {
        List<PlayerDTO> playerDTOList = getAllSuccessRate();
        return playerDTOList
                .stream()
                .filter(p-> p.getSuccessRate() != null)
                .max(Comparator.comparing(PlayerDTO::getSuccessRate)).orElseThrow(NoGamesPlayedException::new);
    }

    @Override
    public PlayerDTO getLoser() {
        List<PlayerDTO> playerDTOList = getAllSuccessRate();
        if(playerDTOList.stream().allMatch(p-> p.getSuccessRate() == null)){
            throw new NoGamesPlayedException();
        }
        return playerDTOList
                .stream()
                .filter(p-> p.getSuccessRate() != null)
                .min(Comparator.comparing(PlayerDTO::getSuccessRate)).orElseThrow(NoGamesPlayedException::new);
    }

//    @Override
//    public PlayerDTO playerToDTO(PlayerEntity playerEntity){
//        double successRate = gameService.getSuccessRate(playerEntity);
//        return new PlayerDTO(playerEntity.getUsername(), successRate);
//    }
//
    @Override
    public PlayerEntity playerDTOToEntity(PlayerDTORequest playerDTORequest){
        return new PlayerEntity(playerDTORequest.getUsername());
    }
}
