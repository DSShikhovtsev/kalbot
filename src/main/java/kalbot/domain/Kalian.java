package kalbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "kalian", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class Kalian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_chat_id")
    private Long userChatId;

    @Column(name = "fortress_id")
    private Long fortressId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "date_to")
    private LocalDateTime dateTo;

    @Column(name = "ice")
    private Boolean ice;

    @OneToMany(targetEntity = Tobacco.class, fetch = FetchType.LAZY)
    @JoinTable(name = "kalians_tobaccos", schema = "main", joinColumns = @JoinColumn(name = "kalian_id"),
        inverseJoinColumns = @JoinColumn(name = "tobacco_id"))
    private Set<Tobacco> tobaccos;
}
