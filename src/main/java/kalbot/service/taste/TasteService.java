package kalbot.service.taste;

import kalbot.domain.Taste;

import java.util.List;

public interface TasteService {
    Taste getById(Long id);

    List<Taste> getAll();

    Taste save(Taste taste);

    void delete(Taste taste);

    List<Taste> getAllByTaste(String taste);

    List<Taste> getTasteByFortress(Integer fortress);

    List<Taste> getByFortressAndGlobalTasteId(Integer fortress, Long globalTasteId);
    List<Taste> getByFortressIdAndGlobalTasteId(Long fortress, Long globalTasteId);
}
