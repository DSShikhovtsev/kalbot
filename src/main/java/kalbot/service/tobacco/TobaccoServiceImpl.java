package kalbot.service.tobacco;

import kalbot.domain.Brand;
import kalbot.domain.Fortress;
import kalbot.domain.Tobacco;
import kalbot.repository.TobaccoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TobaccoServiceImpl implements TobaccoService {

    private final TobaccoRepository repository;

    public TobaccoServiceImpl(TobaccoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tobacco getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Tobacco> getAll() {
        return repository.findAll();
    }

    @Override
    public Tobacco save(Tobacco tobacco) {
        return repository.save(tobacco);
    }

    @Override
    public void delete(Tobacco tobacco) {
        repository.delete(tobacco);
    }

    @Override
    public List<Tobacco> getAllByRating(Float rating) {
        return repository.findAllByRatingBetween(rating.longValue() + 0F, rating.longValue() + 1F);
    }

    @Override
    public List<Tobacco> getAllByFortress(Fortress fortress) {
        return repository.findAllByFortress(fortress);
    }

    @Override
    public Tobacco getByBrandIdAndTasteId(Long brandId, Long tasteId) {
        return repository.findByBrandIdAndTasteId(brandId, tasteId);
    }
}
