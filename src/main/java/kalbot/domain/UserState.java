package kalbot.domain;

import kalbot.bot.BotState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_state", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class UserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_chat_id")
    private Long userChatId;

    @Column(name = "state")
    private BotState state;

    @Column(name = "global_taste_id")
    private Long globalTasteId;

    @ManyToOne(targetEntity = Kalian.class, fetch = FetchType.LAZY)
    @JoinTable(name = "userstate_kalian", schema = "main", joinColumns = @JoinColumn(name = "user_state_id"),
        inverseJoinColumns = @JoinColumn(name = "kalian_id"))
    private Kalian kalian;

    @ManyToMany(targetEntity = Taste.class, fetch = FetchType.LAZY)
    @JoinTable(name = "users_tastes", schema = "main", joinColumns = @JoinColumn(name = "user_state_id"),
            inverseJoinColumns = @JoinColumn(name = "tastes_id"))
    private List<Taste> tastes;

    public UserState(Long userChatId) {
        this.userChatId = userChatId;
    }

    public void resetState() {
        state = BotState.SHOW_MAIN_MENU;
        tastes = new ArrayList<>();
        globalTasteId = null;
    }
}
