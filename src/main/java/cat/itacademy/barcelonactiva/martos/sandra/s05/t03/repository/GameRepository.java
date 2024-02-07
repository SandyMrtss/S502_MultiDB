package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<GameHistory, Integer> {
    GameHistory findByPlayerId(Integer playerId);
}