package expense_tracker.repository;
import org.springframework.data.repository.CrudRepository;
import expense_tracker.entities.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//to interact with db
@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer>
{
    Optional<RefreshToken> findByToken(String token);



}
