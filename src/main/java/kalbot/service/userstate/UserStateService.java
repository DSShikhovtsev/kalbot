package kalbot.service.userstate;

import kalbot.domain.UserState;

import java.util.List;

public interface UserStateService {
    UserState save(UserState userState);

    UserState getById(Long id);

    List<UserState> getAll();

    void delete(UserState userState);

    UserState getByChatId(Long id);
}
