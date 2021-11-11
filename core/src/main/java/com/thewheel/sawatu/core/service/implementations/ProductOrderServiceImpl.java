package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.core.service.interfaces.ProductOrderService;
import com.thewheel.sawatu.database.model.ProductOrder;
import com.thewheel.sawatu.database.repository.ProductOrderRepository;
import com.thewheel.sawatu.shared.constant.MessageConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductOrderDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final Mapper mapper;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public PageDto<ProductOrderDto> list(Pageable page) {
        Page<ProductOrder> productOrders = productOrderRepository.findAll(page);
        return PageDto.<ProductOrderDto>builder()
                .items(productOrders.stream()
                         .map(mapper::fromEntity)
                         .collect(Collectors.toList()))
                .pages(productOrders.getTotalPages())
                .build();
    }

    @Override
    public PageDto<ProductOrderDto> list(Pageable page, String username) {
        Page<ProductOrder> productOrders = productOrderRepository.findAllByUserNameOrderByIdDesc(username, page);
        return PageDto.<ProductOrderDto>builder()
                .items(productOrders.stream()
                                  .map(mapper::fromEntity)
                                  .collect(Collectors.toList()))
                .pages(productOrders.getTotalPages())
                .build();
    }

    @Override
    public ProductOrderDto get(Long id) {
        ProductOrder productOrder = productOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                                                             MessageConstants.PRODUCT_ORDER,
                                                                             id)));
        return mapper.fromEntity(productOrder);
    }

    @Override
    public ProductOrderDto save(ProductOrderDto productOrderDto) {
        ProductOrder productOrder = mapper.toEntity(productOrderDto);
        productOrder = productOrderRepository.save(productOrder);
        return mapper.fromEntity(productOrder);
    }

    @Override
    public void delete(ProductOrderDto productOrderDto) {
        productOrderRepository.deleteById(productOrderDto.getId());
    }
}
