package kalbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fortress", schema = "main")
@Getter
@Setter
@RequiredArgsConstructor
public class Fortress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "fortress")
    private String fortress;
}
