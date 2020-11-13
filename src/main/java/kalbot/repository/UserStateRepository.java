package kalbot.repository;

import kalbot.domain.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStateRepository extends JpaRepository<UserState, Long> {

    UserState findByUserChatId(Long id);
}
