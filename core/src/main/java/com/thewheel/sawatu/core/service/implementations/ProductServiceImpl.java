package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.ProductService;
import com.thewheel.sawatu.database.model.Product;
import com.thewheel.sawatu.database.repository.ProductRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final Mapper mapper;
    private final ProductRepository productRepository;

    @Override
    public PageDto<ProductDto> list(Pageable page) {
        Page<Product> products = productRepository.findAll(page);
        return PageDto.<ProductDto> builder()
                .items(products.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(products.getTotalPages())
                .build();
    }

    @Override
    public List<ProductDto> findByQuery(String query, int page, int size) {
        // TODO
        return null;
    }

    @Override
    public List<ProductDto> findByQuery(int min, int max, int page, int size) {
        // TODO
        return null;
    }

    @Override
    public List<ProductDto> findByQuery(String query, int min, int max, int page, int size) {
        // TODO
        return null;
    }

    @Override
    public ProductDto get(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.PRODUCT,
                                                                             id)));
        return mapper.fromEntity(product);
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = mapper.toEntity(productDto);
        product = productRepository.save(product);
        return mapper.fromEntity(product);
    }

    @Override
    public void delete(ProductDto productDto) {
        productRepository.deleteById(productDto.getId());
    }
}
