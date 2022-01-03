package com.thewheel.sawatu.core.service.implementations;

import com.thewheel.sawatu.database.model.Product;
import com.thewheel.sawatu.database.repository.ProductRepository;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private Mapper mapper;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void list_shouldReturn() {
        // Given
        Product product_1 = Product.builder()
                .id(TestConstants.ID_1)
                .build();
        Product product_2 = Product.builder()
                .id(TestConstants.ID_2)
                .build();
        ProductDto productDto_1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto productDto_2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Page<Product> products = new PageImpl<Product>(Arrays.asList(product_1, product_2));
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(mapper.fromEntity(product_1)).willReturn(productDto_1);
        given(mapper.fromEntity(product_2)).willReturn(productDto_2);
        given(productRepository.findAll(PAGEABLE)).willReturn(products);
        PageDto<ProductDto> expected = PageDto.<ProductDto> builder()
                .items(Arrays.asList(productDto_1, productDto_2))
                .pages(1)
                .build();

        // When
        PageDto<ProductDto> response = productService.list(PAGEABLE);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(mapper, times(1)).fromEntity(product_1);
        verify(mapper, times(1)).fromEntity(product_2);
        verify(mapper, times(2)).fromEntity(any(Product.class));

        verify(productRepository, times(1)).findAll(PAGEABLE);
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Ignore
    public void findByQuery() {
        // TODO
    }

    @Test
    @Ignore
    public void testFindByQuery() {
        // TODO
    }

    @Test
    @Ignore
    public void testFindByQuery1() {
        // TODO
    }

    @Test
    public void get_shouldThrow() {
        // Given
        given(productRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Product> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> productService.get(TestConstants.ID_1));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND,
                                               MessageConstants.PRODUCT,
                                               TestConstants.ID_1));

        verify(productRepository, times(1)).findById(TestConstants.ID_1);
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    public void get_shouldReturn() {
        // Given
        Product product = Product.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(productRepository.findById(TestConstants.ID_1)).willReturn(Optional.<Product> of(product));
        given(mapper.fromEntity(product)).willReturn(dto);

        // When
        ProductDto response = productService.get(TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
        verify(productRepository, times(1)).findById(TestConstants.ID_1);
        verify(productRepository, times(1)).findById(any());

        verify(mapper, times(1)).fromEntity(product);
        verify(mapper, times(1)).fromEntity(any(Product.class));
    }

    @Test
    public void save_shouldReturn() {
        // Given
        Product product = Product.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(productRepository.save(product)).willReturn(product);
        given(mapper.fromEntity(product)).willReturn(dto);
        given(mapper.toEntity(dto)).willReturn(product);

        // When
        ProductDto response = productService.save(dto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
        verify(productRepository, times(1)).save(product);
        verify(productRepository, times(1)).save(any());

        verify(mapper, times(1)).fromEntity(product);
        verify(mapper, times(1)).fromEntity(any(Product.class));

        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toEntity(any(ProductDto.class));
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        ProductDto dto = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(productRepository).deleteById(TestConstants.ID_1);

        // When
        productService.delete(dto);

        // Then
        verify(productRepository, times(1)).deleteById(TestConstants.ID_1);
        verify(productRepository, times(1)).deleteById(any());
    }
}