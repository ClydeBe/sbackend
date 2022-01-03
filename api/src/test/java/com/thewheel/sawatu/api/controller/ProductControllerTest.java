package com.thewheel.sawatu.api.controller;

import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.core.service.interfaces.ProductService;
import com.thewheel.sawatu.constants.TestConstants;
import com.thewheel.sawatu.shared.dto.PageDto;
import com.thewheel.sawatu.shared.dto.ProductDto;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.thewheel.sawatu.constants.MessageConstants.PRODUCT_QUERY_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService service;

    @Test
    public void getAll_shouldReturn() {
        // Given
        ProductDto dto_1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto_2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        PageDto<ProductDto> expected = PageDto.<ProductDto> builder()
                .items(Arrays.asList(dto_1, dto_2))
                .pages(1)
                .build();
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.list(PAGEABLE)).willReturn(expected);

        // When
        PageDto<ProductDto> response = controller.getAll(Optional.of(0), Optional.of(10));

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).list(PAGEABLE);
        verify(service, times(1)).list(any());

    }

    @Test
    public void query_byQuery_shouldReturn() {
        // Given
        ProductDto dto_1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto_2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        List<ProductDto> expected = Arrays.asList(dto_1, dto_2);
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.findByQuery(TestConstants.STRING_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1))
                .willReturn(expected);

        Optional<String> query = Optional.<String> of(TestConstants.STRING_CONSTANT_1);
        Optional<Integer> min = Optional.<Integer> empty();
        Optional<Integer> max = Optional.<Integer> empty();
        Optional<Integer> page = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> size = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);

        // When
        List<ProductDto> response = controller.query(query, min, max, page, size);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).findByQuery(TestConstants.STRING_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1);
    }

    @Test
    public void query_byRangeParams_shouldReturn() {
        // Given
        ProductDto dto_1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto_2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        List<ProductDto> expected = Arrays.asList(dto_1, dto_2);
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.findByQuery((int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1))
                .willReturn(expected);

        Optional<String> query = Optional.<String> empty();
        Optional<Integer> min = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> max = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> page = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> size = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);

        // When
        List<ProductDto> response = controller.query(query, min, max, page, size);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).findByQuery((int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1);
    }

    @Test
    public void query_byQueryAndRangeParams_shouldReturn() {
        // Given
        ProductDto dto_1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto dto_2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        List<ProductDto> expected = Arrays.asList(dto_1, dto_2);
        Pageable PAGEABLE = PageRequest.of(0, 10);
        given(service.findByQuery(TestConstants.STRING_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1,
                                  (int) TestConstants.NUMERIC_CONSTANT_1))
                .willReturn(expected);

        Optional<String> query = Optional.<String> of(TestConstants.STRING_CONSTANT_1);
        Optional<Integer> min = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> max = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> page = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);
        Optional<Integer> size = Optional.<Integer> of((int) TestConstants.NUMERIC_CONSTANT_1);

        // When
        List<ProductDto> response = controller.query(query, min, max, page, size);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).findByQuery(TestConstants.STRING_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1,
                                              (int) TestConstants.NUMERIC_CONSTANT_1);
    }

    @Test
    public void query_shouldThrow() {
        // Given

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> controller.query(Optional.empty(),
                                       Optional.empty(),
                                       Optional.empty(),
                                       Optional.empty(),
                                       Optional.empty()));

        // Then
        response.isNotNull()
                .isInstanceOf(BadRequestException.class)
                .withFailMessage(PRODUCT_QUERY_EXCEPTION);
    }

    @Test
    public void get_shouldReturn() {
        // Given
        ProductDto expected = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.get(TestConstants.ID_1)).willReturn(expected);

        // When
        ProductDto response = controller.get(TestConstants.ID_1);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).get(TestConstants.ID_1);
        verify(service, times(1)).get(any());
    }

    @Test
    public void create_shouldReturn() {
        // Given
        ProductDto expected = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        ProductDto response = controller.create(expected);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected);
        verify(service, times(1)).save(any());
    }

    @Test
    public void update_shouldReturn() {
        // Given
        ProductDto expected = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        given(service.save(expected)).willReturn(expected);

        // When
        ProductDto response = controller.update(expected);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);
        verify(service, times(1)).save(expected);
        verify(service, times(1)).save(any());
    }

    @Test
    public void delete_shouldReturn() {
        // Given
        ProductDto expected = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        doNothing().when(service).delete(expected);

        // When
        controller.delete(expected);

        // Then
        verify(service, times(1)).delete(expected);
        verify(service, times(1)).delete(any());
    }

}