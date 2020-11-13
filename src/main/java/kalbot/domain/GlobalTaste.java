package kalbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "global_taste", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class GlobalTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taste")
    private String taste;

    @ManyToMany(targetEntity = Taste.class, fetch = FetchType.LAZY)
    @JoinTable(name = "globaltaste_tastes", schema = "main", joinColumns = @JoinColumn(name = "global_taste_id"),
        inverseJoinColumns = @JoinColumn(name = "taste_id"))
    private List<Taste> tastes;
}
