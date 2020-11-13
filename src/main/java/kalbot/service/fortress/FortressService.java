package kalbot.service.fortress;

import kalbot.domain.Fortress;

import java.util.List;

public interface FortressService {
    Fortress getById(Long id);

    List<Fortress> getAll();

    Fortress save(Fortress fortress);

    void delete(Fortress fortress);

    Fortress getByScore(Integer score);

    List<Fortress> getAllByFortress(String fortress);
}
