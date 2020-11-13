package kalbot.repository;

import kalbot.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "select b from Brand b where b.id in " +
            "(select tb.brand.id from Tobacco tb where tb.taste.id = " +
            "(select t.id from Taste t where t.id = :tasteId))")
    List<Brand> findAllByTasteId(@Param("tasteId") Long tasteId);
}
