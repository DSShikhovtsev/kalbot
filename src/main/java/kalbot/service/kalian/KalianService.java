package kalbot.service.kalian;

import kalbot.domain.Kalian;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KalianService {
    @Transactional
    Kalian getById(Long id);

    @Transactional
    List<Kalian> getAll();

    @Transactional
    Kalian save(Kalian kalian);
}
