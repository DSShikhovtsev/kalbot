package kalbot.service.brand;

import kalbot.domain.Brand;

import java.util.List;

public interface BrandService {

    Brand getById(Long id);

    List<Brand> getAll();

    Brand save(Brand brand);

    void delete(Brand brand);

    List<Brand> getAllByTasteId(Long tasteId);
}
