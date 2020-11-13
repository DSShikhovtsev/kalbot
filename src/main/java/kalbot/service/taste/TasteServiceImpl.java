package kalbot.service.taste;

import kalbot.domain.Taste;
import kalbot.repository.TasteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TasteServiceImpl implements TasteService {

    private final TasteRepository repository;

    public TasteServiceImpl(TasteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Taste getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Taste> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Taste save(Taste taste) {
        return repository.save(taste);
    }

    @Transactional
    @Override
    public void delete(Taste taste) {
        repository.delete(taste);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Taste> getAllByTaste(String taste) {
        return repository.findAllByTaste(taste);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Taste> getTasteByFortress(Integer fortress) {
        return repository.findByFortress(fortress);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Taste> getByFortressAndGlobalTasteId(Integer fortress, Long globalTasteId) {
        return repository.findAllByFortressAndGlobalTaste(fortress, globalTasteId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Taste> getByFortressIdAndGlobalTasteId(Long fortress, Long globalTasteId) {
        return repository.findAllByFortressIdAndGlobalTaste(fortress, globalTasteId);
    }
}
