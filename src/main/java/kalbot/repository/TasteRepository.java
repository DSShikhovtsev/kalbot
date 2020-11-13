package kalbot.repository;

import kalbot.domain.Taste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasteRepository extends JpaRepository<Taste, Long> {

    List<Taste> findAllByTaste(String taste);
    Taste findByDescription(String description);

    @Query(value = "select t from Taste t where t.id in " +
            "(select tb.taste.id from Tobacco tb where tb.fortress.id = " +
            "(select f.id from Fortress f where f.score = :fortress))" +
            " group by t.taste")
    List<Taste> findByFortress(@Param("fortress") Integer fortress);

    @Query(value = "select t from Taste t where t.id in " +
            "(select tb.taste.id from Tobacco tb where tb.fortress.id = " +
            "(select f.id from Fortress f where f.score = :fortress)) " +
            "and (select gt from GlobalTaste gt where gt.id = :globalTasteId) MEMBER OF t.globalTastes " +
            "group by t.taste")
    List<Taste> findAllByFortressAndGlobalTaste(@Param("fortress") Integer fortressId,
                                                @Param("globalTasteId") Long globalTasteId);

    @Query(value = "select t from Taste t where t.id in " +
            "(select tb.taste.id from Tobacco tb where tb.fortress.id = :fortress) " +
            "and (select gt from GlobalTaste gt where gt.id = :globalTasteId) MEMBER OF t.globalTastes ")
    List<Taste> findAllByFortressIdAndGlobalTaste(@Param("fortress") Long fortressId,
                                                @Param("globalTasteId") Long globalTasteId);
}
