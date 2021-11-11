package com.thewheel.sawatu.core.service.interfaces;

import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductOrderDto;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductOrderService {
    PageDto<ProductOrderDto> list(Pageable page);
    PageDto<ProductOrderDto> list(Pageable page, String username);
    ProductOrderDto get(Long id);
    ProductOrderDto save(ProductOrderDto ProductOrderDto);
    void delete(ProductOrderDto ProductOrderDto);
}
