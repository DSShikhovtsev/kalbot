package kalbot.service.tobacco;

import kalbot.domain.Brand;
import kalbot.domain.Fortress;
import kalbot.domain.Tobacco;

import java.util.List;

public interface TobaccoService {
    Tobacco getById(Long id);

    List<Tobacco> getAll();

    Tobacco save(Tobacco tobacco);

    void delete(Tobacco tobacco);

    List<Tobacco> getAllByRating(Float rating);

    List<Tobacco> getAllByFortress(Fortress fortress);

    Tobacco getByBrandIdAndTasteId(Long brandId, Long tasteId);
}
