package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.controllers;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.*;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/diceGame/v1/players")
public class Controller {
    private PlayerService playerService;

    public Controller(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping("")
    public ResponseEntity<String> newPlayer(@RequestBody PlayerDTORequest playerDTORequest){
        playerService.addPlayer(playerDTORequest);
        return new ResponseEntity<>("Player added successfully", HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<PlayerDTO>> getAllSuccessRate(){
        List<PlayerDTO> playerDTOList = playerService.getAllSuccessRate();
        return new ResponseEntity <>(playerDTOList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable("id") Integer id, @RequestBody PlayerDTORequest playerDTORequest){
        playerService.updatePlayer(id, playerDTORequest);
        return new ResponseEntity<>("Player updated successfully", HttpStatus.OK);
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> play(@PathVariable("id") Integer id){
        GameDTO newGame = playerService.playGame(id);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/games")
    public ResponseEntity<String> deleteAllGames(@PathVariable("id") Integer id){
        playerService.deleteAllGames(id);
        return new ResponseEntity<>("All games deleted successfully", HttpStatus.OK);
    }



    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameDTO>> getAllGames(@PathVariable("id") Integer id){
        List<GameDTO> allGames = playerService.getAllGames(id);
        return new ResponseEntity<>(allGames, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<Double> getAverageRate(){
        double avgSuccess = playerService.getAverageSuccessRate();
        return new ResponseEntity <>(avgSuccess, HttpStatus.OK);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser(){
        PlayerDTO loser = playerService.getLoser();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner(){
        PlayerDTO winner = playerService.getWinner();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }
}