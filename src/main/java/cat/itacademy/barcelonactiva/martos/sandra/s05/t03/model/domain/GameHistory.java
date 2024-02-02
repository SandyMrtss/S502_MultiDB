package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "games")
public class GameHistory {
    @Id
    private String _id;

    @Indexed(unique = true)
    private Integer playerId;

    private List<Game> allGames;
    private Double successRate;

    public GameHistory(Integer playerId) {
        this.playerId = playerId;
        this.allGames = new ArrayList<>();
        this.successRate = null;
    }

    public void addGame(Game game){
        allGames.add(game);
    }
}