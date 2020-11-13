package kalbot.repository;

import kalbot.domain.Fortress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FortressRepository extends JpaRepository<Fortress, Long> {

    Fortress findByScore(Integer score);

    List<Fortress> findAllByFortress(String fortress);
}
