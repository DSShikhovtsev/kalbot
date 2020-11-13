package kalbot.repository;

import kalbot.domain.GlobalTaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GlobalTasteRepository extends JpaRepository<GlobalTaste, Long> {

    GlobalTaste findByTaste(String taste);

    @Query(value = "select tbc.taste.globalTastes from Tobacco tbc " +
            "where tbc.fortress.id = :fortressId")
//    @Query(value = "select gt from GlobalTaste gt " +
//            "join gt.tastes t " +
//            "join Tobacco tbc on tbc.taste = t " +
//            "where tbc.fortress.id = :fortressId")
    List<GlobalTaste> findAllWithTasteForFortress(@Param(value = "fortressId") Long fortressId);
}
