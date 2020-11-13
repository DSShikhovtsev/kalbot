package kalbot.repository;

import kalbot.domain.Fortress;
import kalbot.domain.Tobacco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TobaccoRepository extends JpaRepository<Tobacco, Long> {

    List<Tobacco> findAllByRatingBetween(Float start, Float finish);

    List<Tobacco> findAllByFortress(Fortress fortress);

    Tobacco findByBrandIdAndTasteId(Long brandId, Long tasteId);
}
