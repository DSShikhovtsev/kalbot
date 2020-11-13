package kalbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tobacco", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class Tobacco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Float rating;

    @ManyToOne(targetEntity = Taste.class, fetch = FetchType.LAZY)
    @JoinTable(name = "tobaccos_tastes", schema = "main", joinColumns = @JoinColumn(name = "tobacco_id"),
        inverseJoinColumns = @JoinColumn(name = "taste_id"))
    private Taste taste;

    @ManyToOne(targetEntity = Fortress.class, fetch = FetchType.LAZY)
    @JoinTable(name = "fortress_tobaccos", schema = "main", joinColumns = @JoinColumn(name = "tobacco_id"),
        inverseJoinColumns = @JoinColumn(name = "fortress_id"))
    private Fortress fortress;

    @ManyToOne(targetEntity = Brand.class, fetch = FetchType.LAZY)
    @JoinTable(name = "brands_tobaccos", schema = "main", joinColumns = @JoinColumn(name = "tobacco_id"),
        inverseJoinColumns = @JoinColumn(name = "brand_id"))
    private Brand brand;
}
