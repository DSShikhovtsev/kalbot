package kalbot.service.globaltaste;

import kalbot.domain.GlobalTaste;
import kalbot.repository.GlobalTasteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalTasteServiceImpl implements GlobalTasteService {

    private final GlobalTasteRepository repository;

    public GlobalTasteServiceImpl(GlobalTasteRepository repository) {
        this.repository = repository;
    }

    @Override
    public GlobalTaste getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<GlobalTaste> getAll() {
        return repository.findAll();
    }

    @Override
    public List<GlobalTaste> getAllWithTasteForFortress(Long fortressId) {
        List<GlobalTaste> list = repository.findAllWithTasteForFortress(fortressId);
        return new ArrayList<>(new HashSet<>(list)); //TODO фу костыль
    }

    @Override
    public GlobalTaste save(GlobalTaste globalTaste) {
        return repository.save(globalTaste);
    }

    @Override
    public void delete(GlobalTaste globalTaste) {
        repository.delete(globalTaste);
    }

    @Override
    public GlobalTaste getByTaste(String taste) {
        return repository.findByTaste(taste);
    }
}
