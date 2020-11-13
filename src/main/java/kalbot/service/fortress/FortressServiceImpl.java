package kalbot.service.fortress;

import kalbot.domain.Fortress;
import kalbot.repository.FortressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FortressServiceImpl implements FortressService {

    private final FortressRepository repository;

    public FortressServiceImpl(FortressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Fortress getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Fortress> getAll() {
        return repository.findAll();
    }

    @Override
    public Fortress save(Fortress fortress) {
        return repository.save(fortress);
    }

    @Override
    public void delete(Fortress fortress) {
        repository.delete(fortress);
    }

    @Override
    public Fortress getByScore(Integer score) {
        return repository.findByScore(score);
    }

    @Override
    public List<Fortress> getAllByFortress(String fortress) {
        return repository.findAllByFortress(fortress);
    }
}
