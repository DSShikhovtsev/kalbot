package kalbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "taste", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class Taste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "taste")
    private String taste;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = GlobalTaste.class, fetch = FetchType.LAZY)
    @JoinTable(name = "globaltaste_tastes", schema = "main", joinColumns = @JoinColumn(name = "taste_id"),
        inverseJoinColumns = @JoinColumn(name = "global_taste_id"))
    private List<GlobalTaste> globalTastes;
}
