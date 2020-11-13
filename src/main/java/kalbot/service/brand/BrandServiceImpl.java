package kalbot.service.brand;

import kalbot.domain.Brand;
import kalbot.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository repository;

    public BrandServiceImpl(BrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public Brand getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return repository.findAll();
    }

    @Override
    public Brand save(Brand brand) {
        return repository.save(brand);
    }

    @Override
    public void delete(Brand brand) {
        repository.delete(brand);
    }

    @Override
    public List<Brand> getAllByTasteId(Long tasteId) {
        return repository.findAllByTasteId(tasteId);
    }
}
