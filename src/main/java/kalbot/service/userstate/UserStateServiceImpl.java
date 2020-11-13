package kalbot.service.userstate;

import kalbot.domain.UserState;
import kalbot.repository.UserStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserStateServiceImpl implements UserStateService {

    private final UserStateRepository repository;

    public UserStateServiceImpl(UserStateRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserState save(UserState userState) {
        return repository.save(userState);
    }

    @Transactional
    @Override
    public UserState getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public List<UserState> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void delete(UserState userState) {
        repository.delete(userState);
    }

    @Transactional
    @Override
    public UserState getByChatId(Long id) {
        return repository.findByUserChatId(id);
    }
}
