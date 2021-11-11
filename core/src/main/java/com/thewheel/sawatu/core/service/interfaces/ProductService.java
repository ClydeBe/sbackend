package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    PageDto<ProductDto> list(Pageable page);
    public List<ProductDto> findByQuery(String query, int page, int size);
    public List<ProductDto> findByQuery(int min, int max, int page, int size);
    public List<ProductDto> findByQuery(String query, int min, int max, int page, int size);
    ProductDto get(Long id);
    ProductDto save(ProductDto productDto);
    void delete(ProductDto productDto);
}
