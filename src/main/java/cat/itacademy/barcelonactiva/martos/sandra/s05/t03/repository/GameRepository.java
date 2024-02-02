package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.repository;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<GameHistory, Integer> {
    public List<GameHistory> findByPlayerId(Integer playerId);

    public void deleteByPlayerId(Integer playerId);
}