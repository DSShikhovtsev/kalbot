package kalbot.service.globaltaste;

import kalbot.domain.GlobalTaste;

import java.util.List;

public interface GlobalTasteService {

    GlobalTaste getById(Long id);

    List<GlobalTaste> getAll();

    List<GlobalTaste> getAllWithTasteForFortress(Long fortressId);

    GlobalTaste save(GlobalTaste globalTaste);

    void delete(GlobalTaste globalTaste);

    GlobalTaste getByTaste(String taste);
}
