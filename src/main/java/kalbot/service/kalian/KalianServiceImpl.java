package kalbot.service.kalian;

import kalbot.domain.Kalian;
import kalbot.repository.KalianRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KalianServiceImpl implements KalianService {

    private final KalianRepository repository;

    public KalianServiceImpl(KalianRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Kalian getById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Kalian> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Kalian save(Kalian kalian) {
        return repository.save(kalian);
    }

    @Transactional
    public void delete(Kalian kalian) {
        repository.delete(kalian);
    }
}
