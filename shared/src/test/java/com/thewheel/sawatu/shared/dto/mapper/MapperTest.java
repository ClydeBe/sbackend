package com.thewheel.sawatu.shared.dto.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewheel.sawatu.database.model.*;
import com.thewheel.sawatu.database.repository.PostRepository;
import com.thewheel.sawatu.database.repository.UserRepository;
import com.thewheel.sawatu.basic.constant.MessageConstants;
import com.thewheel.sawatu.basic.constant.TestConstants;
import com.thewheel.sawatu.shared.dto.*;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MapperTest {

    @InjectMocks
    private Mapper mapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void fromEntity_AppointmentShouldReturnNull_ifParamIsNull() {
        // Given
        Appointment appointment = null;

        // When
        AppointmentDto dto = mapper.fromEntity(appointment);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_AvailabilityShouldReturnNull_ifParamIsNull() {
        // Given
        Availability availability = null;

        // When
        AvailabilityDto dto = mapper.fromEntity(availability);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_CommentShouldReturnNull_ifParamIsNull() {
        // Given
        Comment comment = null;

        // When
        CommentDto dto = mapper.fromEntity(comment);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_HaircutShouldReturnNull_ifParamIsNull() {
        // Given
        Haircut haircut = null;

        // When
        HaircutDto dto = mapper.fromEntity(haircut);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_HaircutOrderShouldReturnNull_ifParamIsNull() {
        // Given
        HaircutOrder haircutOrder = null;

        // When
        HaircutOrderDto dto = mapper.fromEntity(haircutOrder);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_PostShouldReturnNull_ifParamIsNull() {
        // Given
        Post post = null;

        // When
        PostDto dto = mapper.fromEntity(post);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_ProductShouldReturnNull_ifParamIsNull() {
        // Given
        Product product = null;

        // When
        ProductDto dto = mapper.fromEntity(product);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_ProductOrderShouldReturnNull_ifParamIsNull() {
        // Given
        ProductOrder productOrder = null;

        // When
        ProductOrderDto dto = mapper.fromEntity(productOrder);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_ReviewsShouldReturnNull_ifParamIsNull() {
        // Given
        Reviews reviews = null;

        // When
        ReviewDto dto = mapper.fromEntity(reviews);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_StatisticsShouldReturnNull_ifParamIsNull() {
        // Given
        Statistic statistic = null;

        // When
        StatisticsDto dto = mapper.fromEntity(statistic);

        //Then
        assertThat(dto).isNull();
    }

    @Test
    public void fromEntity_AppointmentShouldReturnCorrectMapping() {
        // Given
        User client = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        User vendor = User.builder()
                .password(TestConstants.PASSWORD_2)
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_VENDOR)
                .build();
        UserDto clientDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto vendorDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_VENDOR)
                .build();
        Appointment appointment = Appointment.builder()
                .isVendorAddress(true)
                .client(client)
                .vendor(vendor)
                .dateTime(LocalDateTime.MAX)
                .updatedAt(LocalDateTime.MAX)
                .build();
        AppointmentDto dto = AppointmentDto.builder()
                .isVendorAddress(true)
                .client(clientDto)
                .vendor(vendorDto)
                .dateTime(LocalDateTime.MAX)
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        AppointmentDto response = mapper.fromEntity(appointment);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @SneakyThrows
    @Test
    public void fromEntity_AvailabilityShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        PeriodDto period = PeriodDto.builder()
                .periods(Arrays.asList(16))
                .day(LocalDate.MAX)
                .build();
        given(objectMapper.readValue(TestConstants.AVAILABILITY_1, List.class)).willReturn(Arrays.asList(period));
        Availability availability = Availability.builder()
                .user(user)
                .availabilities(TestConstants.AVAILABILITY_1)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .user(userDto)
                .availabilities(Arrays.asList(period))
                .build();

        // When
        AvailabilityDto response = mapper.fromEntity(availability);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_CommentShouldReturnCorrectMapping() {
        // Given
        User author = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto authorDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Post post = Post.builder()
                .body(TestConstants.BODY_1)
                .author(author)
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .replyId(2L)
                .body(TestConstants.BODY_1)
                .post(post)
                .author(author)
                .comments(Arrays.asList())
                .updatedAt(LocalDateTime.MAX)
                .build();
        CommentDto dto = CommentDto.builder()
                .id(1L)
                .replyId(2L)
                .body(TestConstants.BODY_1)
                .author(authorDto)
                .comments(Arrays.asList())
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        CommentDto response = mapper.fromEntity(comment);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_HaircutShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Haircut haircut = Haircut.builder()
                .id(1L)
                .vendor(user)
                .photo(TestConstants.PHOTO_1)
                .duration((short) TestConstants.DURATION_1)
                .label(TestConstants.LABEL_1)
                .price(TestConstants.PRICE_1)
                .vatRatio(TestConstants.VAT_RATIO_1)
                .updatedAt(LocalDateTime.MAX)
                .build();
        HaircutDto dto = HaircutDto.builder()
                .id(1L)
                .vendor(userDto)
                .photo(TestConstants.PHOTO_1)
                .duration(TestConstants.DURATION_1)
                .label(TestConstants.LABEL_1)
                .price(TestConstants.PRICE_1)
                .vatRatio(TestConstants.VAT_RATIO_1)
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        HaircutDto response = mapper.fromEntity(haircut);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_HaircutOrderShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        HaircutOrder haircutOrder = HaircutOrder.builder()
                .id(1L)
                .client(user)
                .description(TestConstants.DESCRIPTION_1)
                .date(LocalDateTime.MAX)
                .updatedAt(LocalDateTime.MAX)
                .price(TestConstants.PRICE_1)
                .build();
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(1L)
                .client(userDto)
                .description(TestConstants.DESCRIPTION_1)
                .date(LocalDateTime.MAX)
                .updatedAt(LocalDateTime.MAX)
                .price(TestConstants.PRICE_1)
                .appointments(Arrays.asList())
                .build();

        // When
        HaircutOrderDto response = mapper.fromEntity(haircutOrder);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_PostShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Post post = Post.builder()
                .body(TestConstants.BODY_1)
                .author(user)
                .cover(TestConstants.STRING_CONSTANT_1)
                .tags(TestConstants.STRING_CONSTANT_2)
                .title(TestConstants.STRING_CONSTANT_1)
                .updatedAt(LocalDateTime.MAX)
                .build();
        PostDto dto = PostDto.builder()
                .body(TestConstants.BODY_1)
                .author(userDto)
                .cover(TestConstants.STRING_CONSTANT_1)
                .tags(TestConstants.STRING_CONSTANT_2)
                .title(TestConstants.STRING_CONSTANT_1)
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        PostDto response = mapper.fromEntity(post);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_ProductShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Product product = Product.builder()
                .id(1L)
                .label(TestConstants.LABEL_1)
                .vendor(user)
                .characteristics(TestConstants.STRING_CONSTANT_1)
                .description(TestConstants.STRING_CONSTANT_2)
                .usage(TestConstants.STRING_CONSTANT_3)
                .image(TestConstants.STRING_CONSTANT_4)
                .price(TestConstants.PRICE_1)
                .quantity((int) TestConstants.NUMERIC_CONSTANT_1)
                .updatedAt(LocalDateTime.MAX)
                .build();
        ProductDto dto = ProductDto.builder()
                .id(1L)
                .label(TestConstants.LABEL_1)
                .vendor(userDto)
                .characteristics(TestConstants.STRING_CONSTANT_1)
                .description(TestConstants.STRING_CONSTANT_2)
                .usage(TestConstants.STRING_CONSTANT_3)
                .image(TestConstants.STRING_CONSTANT_4)
                .price(TestConstants.PRICE_1)
                .quantity((int) TestConstants.NUMERIC_CONSTANT_1)
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        ProductDto response = mapper.fromEntity(product);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @SneakyThrows
    @Test
    public void fromEntity_ProductOrderShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        ProductOrder productOrder = ProductOrder.builder()
                .id(1L)
                .user(user)
                .items(TestConstants.STRING_CONSTANT_2)
                .updatedAt(LocalDateTime.MAX)
                .build();
        ProductDto p1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto p2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Map<ProductDto, Integer> items = new HashMap<>(){{
            put(p1, (int) TestConstants.NUMERIC_CONSTANT_1);
            put(p2, (int) TestConstants.NUMERIC_CONSTANT_2);
        }};
        ProductOrderDto dto = ProductOrderDto.builder()
                .id(1L)
                .user(userDto)
                .items(items)
                .updatedAt(LocalDateTime.MAX)
                .build();
        given(objectMapper.readValue(TestConstants.STRING_CONSTANT_2, Map.class)).willReturn(items);

        // When
        ProductOrderDto response = mapper.fromEntity(productOrder);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_ReviewsShouldReturnCorrectMapping() {
        // Given
        User reviewer = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto reviewerDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        User self = User.builder()
                .password(TestConstants.PASSWORD_2)
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto selfDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_USER)
                .build();
        Reviews reviews = Reviews.builder()
                .id(1L)
                .rating((int) TestConstants.NUMERIC_CONSTANT_1)
                .reviewer(reviewer)
                .self(self)
                .comment(TestConstants.BODY_1)
                .build();
        ReviewDto dto = ReviewDto.builder()
                .id(1L)
                .rating((int) TestConstants.NUMERIC_CONSTANT_1)
                .reviewer(reviewerDto)
                .self(selfDto)
                .comment(TestConstants.BODY_1)
                .build();

        // When
        ReviewDto response = mapper.fromEntity(reviews);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void fromEntity_StatisticsShouldReturnCorrectMapping() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        UserDto userDto = UserDto.builder()
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Statistic statistic = Statistic.builder()
                .id(1L)
//                .rate((int) TestConstants.NUMERIC_CONSTANT_1)
//                .user(user)
//                .followersCount((int) TestConstants.NUMERIC_CONSTANT_2)
//                .haircutCount((int) TestConstants.NUMERIC_CONSTANT_1)
                .build();
        StatisticsDto dto = StatisticsDto.builder()
                .id(1L)
                .rate((int) TestConstants.NUMERIC_CONSTANT_1)
                .user(userDto)
                .followersCount((int) TestConstants.NUMERIC_CONSTANT_2)
                .haircutCount((int) TestConstants.NUMERIC_CONSTANT_1)
                .build();


        // When
        StatisticsDto response = mapper.fromEntity(statistic);

        //Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    public void toEntity_AppointmentShouldReturnNull_ifParamIsNull() {
        // Given
        AppointmentDto dto = null;

        // When
        Appointment entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_AvailabilityShouldReturnNull_ifParamIsNull() {
        // Given
        AvailabilityDto dto = null;

        // When
        Availability entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_CommentShouldReturnNull_ifParamIsNull() {
        // Given
        CommentDto dto = null;

        // When
        Comment entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_HaircutShouldReturnNull_ifParamIsNull() {
        // Given
        HaircutDto dto = null;

        // When
        Haircut entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_HaircutOrderShouldReturnNull_ifParamIsNull() {
        // Given
        HaircutOrderDto dto = null;

        // When
        HaircutOrder entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_PostShouldReturnNull_ifParamIsNull() {
        // Given
        PostDto dto = null;

        // When
        Post entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_ProductShouldReturnNull_ifParamIsNull() {
        // Given
        ProductDto dto = null;

        // When
        Product entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_ReviewShouldReturnNull_ifParamIsNull() {
        // Given
        ReviewDto dto = null;

        // When
        Reviews entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_StatisticsShouldReturnNull_ifParamIsNull() {
        // Given
        StatisticsDto dto = null;

        // When
        Statistic entity = mapper.toEntity(dto);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    public void toEntity_AppointmentShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        AppointmentDto dto = AppointmentDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_AvailabilityShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_CommentShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        CommentDto dto = CommentDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_HaircutShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        HaircutDto dto = HaircutDto.builder()
                .id(1L)
                .duration(TestConstants.DURATION_1)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_HaircutOrderShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_PostThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        PostDto dto = PostDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_ProductShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        ProductDto dto = ProductDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_ProductOrderShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        ProductOrderDto dto = ProductOrderDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_ReviewsShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        ReviewDto dto = ReviewDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_StatisticsShouldThrow_ifNoUserIsAssociatedToAnyUsername() {
        // Given
        StatisticsDto dto = StatisticsDto.builder()
                .id(1L)
                .build();

        given(userRepository.findByUsernameOrEmail(null)).willReturn(Optional.<User> empty());

        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(() -> mapper.toEntity(dto));

        // Then
        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.USER_NOT_FOUND, null));
    }

    @Test
    public void toEntity_AppointmentShouldReturnCorrectEntity() {
        // Given
        User client = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        User vendor = User.builder()
                .password(TestConstants.PASSWORD_2)
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_2)
                .role(TestConstants.ROLE_VENDOR)
                .build();
        AppointmentDto dto = AppointmentDto.builder()
                .id(1L)
                .vendorName(TestConstants.USERNAME_2)
                .clientName(TestConstants.USERNAME_1)
                .isVendorAddress(true)
                .updatedAt(LocalDateTime.MAX)
                .dateTime(LocalDateTime.MAX)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(
                Optional.<User> of(client));
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_2)).willReturn(
                Optional.<User> of(vendor));

        Appointment expected = Appointment.builder()
                .id(1L)
                .isVendorAddress(true)
                .updatedAt(LocalDateTime.MAX)
                .dateTime(LocalDateTime.MAX)
                .client(client)
                .vendor(vendor)
                .build();

        // When
        Appointment response = mapper.toEntity(dto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(2)).findByUsernameOrEmail(any());
    }

    @SneakyThrows
    @Test
    public void toEntity_AvailabilityShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        PeriodDto period = PeriodDto.builder()
                .periods(Arrays.asList(16))
                .day(LocalDate.MAX)
                .build();
        AvailabilityDto dto = AvailabilityDto.builder()
                .id(1L)
                .userName(TestConstants.USERNAME_1)
                .availabilities(Arrays.asList(period))
                .updatedAt(LocalDateTime.MAX)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));
        given(objectMapper.writeValueAsString(Arrays.asList(period))).willReturn(TestConstants.AVAILABILITY_1);
        Availability expected = expected = Availability.builder()
                .id(1L)
                .user(user)
                .availabilities(TestConstants.AVAILABILITY_1)
                .updatedAt(LocalDateTime.MAX)
                .build();

        // When
        Availability response = mapper.toEntity(dto);

        // Then
        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_CommentShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        Post post = Post.builder()
                .id(1L)
                .build();
        CommentDto dto = CommentDto.builder()
                .authorName(TestConstants.USERNAME_1)
                .updatedAt(LocalDateTime.MAX)
                .body(TestConstants.BODY_1)
                .replyId(0L)
                .postId(1L)
                .build();

        given(postRepository.findById(1L)).willReturn(Optional.<Post> of(post));
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        Comment expected = Comment.builder()
                .author(user)
                .updatedAt(LocalDateTime.MAX)
                .body(TestConstants.BODY_1)
                .replyId(0L)
                .post(post)
                .build();

        // When
        Comment response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(any());
    }

    @Test
    public void toEntity_CommentShouldThrowAnException_ifCommentIsAssociatedWithNoPost() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        CommentDto dto = CommentDto.builder()
                .comments(Arrays.asList())
                .authorName(TestConstants.USERNAME_1)
                .updatedAt(LocalDateTime.MAX)
                .body(TestConstants.BODY_1)
                .replyId(0L)
                .postId(1L)
                .build();

        given(postRepository.findById(1L)).willReturn(Optional.<Post> empty());
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));


        // When
        AbstractThrowableAssert<?, ? extends Throwable> response = assertThatThrownBy(
                () -> mapper.toEntity(dto))
                .isInstanceOf(EntityNotFoundException.class);

        // Then

        response.isNotNull()
                .isInstanceOf(EntityNotFoundException.class)
                .withFailMessage(String.format(MessageConstants.ENTITY_NOT_FOUND, MessageConstants.POST, 1L));

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(any());
    }

    @Test
    public void toEntity_HaircutShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        HaircutDto dto = HaircutDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .vendorName(TestConstants.USERNAME_1)
                .vatRatio(TestConstants.VAT_RATIO_1)
                .photo(TestConstants.STRING_CONSTANT_1)
                .label(TestConstants.STRING_CONSTANT_2)
                .price(TestConstants.PRICE_1)
                .duration(TestConstants.DURATION_1)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        Haircut expected = Haircut.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .vendor(user)
                .vatRatio(TestConstants.VAT_RATIO_1)
                .photo(TestConstants.STRING_CONSTANT_1)
                .label(TestConstants.STRING_CONSTANT_2)
                .price(TestConstants.PRICE_1)
                .duration((short) TestConstants.DURATION_1)
                .build();

        // When
        Haircut response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_HaircutOrderShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        HaircutOrderDto dto = HaircutOrderDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .clientName(TestConstants.USERNAME_1)
                .price(TestConstants.PRICE_1)
                .description(TestConstants.STRING_CONSTANT_1)
                .date(LocalDateTime.MAX)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        HaircutOrder expected = HaircutOrder.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .client(user)
                .price(TestConstants.PRICE_1)
                .description(TestConstants.STRING_CONSTANT_1)
                .date(LocalDateTime.MAX)
                .build();

        // When
        HaircutOrder response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_PostShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        PostDto dto = PostDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .body(TestConstants.BODY_1)
                .cover(TestConstants.STRING_CONSTANT_3)
                .tags(TestConstants.STRING_CONSTANT_2)
                .authorName(TestConstants.USERNAME_1)
                .title(TestConstants.STRING_CONSTANT_1)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        Post expected = Post.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .body(TestConstants.BODY_1)
                .cover(TestConstants.STRING_CONSTANT_3)
                .tags(TestConstants.STRING_CONSTANT_2)
                .author(user)
                .title(TestConstants.STRING_CONSTANT_1)
                .build();

        // When
        Post response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_ProductShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        ProductDto dto = ProductDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .usage(TestConstants.STRING_CONSTANT_3)
                .quantity((int) TestConstants.NUMERIC_CONSTANT_1)
                .price(TestConstants.PRICE_1)
                .label(TestConstants.LABEL_1)
                .image(TestConstants.STRING_CONSTANT_4)
                .description(TestConstants.DESCRIPTION_1)
                .characteristics(TestConstants.STRING_CONSTANT_1)
                .vendorName(TestConstants.USERNAME_1)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        Product expected = Product.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .usage(TestConstants.STRING_CONSTANT_3)
                .quantity((int) TestConstants.NUMERIC_CONSTANT_1)
                .price(TestConstants.PRICE_1)
                .label(TestConstants.LABEL_1)
                .image(TestConstants.STRING_CONSTANT_4)
                .description(TestConstants.DESCRIPTION_1)
                .characteristics(TestConstants.STRING_CONSTANT_1)
                .vendor(user)
                .build();

        // When
        Product response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @SneakyThrows
    @Test
    public void toEntity_ProductOrderShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        ProductDto p1 = ProductDto.builder()
                .id(TestConstants.ID_1)
                .build();
        ProductDto p2 = ProductDto.builder()
                .id(TestConstants.ID_2)
                .build();
        Map<ProductDto, Integer> items = new HashMap<>(){{
            put(p1, (int) TestConstants.NUMERIC_CONSTANT_1);
            put(p2, (int) TestConstants.NUMERIC_CONSTANT_2);
        }};
        ProductOrderDto dto = ProductOrderDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .userName(TestConstants.USERNAME_1)
                .items(items)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        ProductOrder expected = ProductOrder.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .user(user)
                .items(TestConstants.STRING_CONSTANT_1)
                .build();
        given(objectMapper.writeValueAsString(items)).willReturn(TestConstants.STRING_CONSTANT_1);

        // When
        ProductOrder response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_ReviewsShouldReturnCorrectEntity() {
        // Given
        User reviewer = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        User self = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_2)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        ReviewDto dto = ReviewDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .selfName(TestConstants.USERNAME_2)
                .reviewerName(TestConstants.USERNAME_1)
                .rating((int) TestConstants.NUMERIC_CONSTANT_1)
                .comment(TestConstants.BODY_1)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(reviewer));
        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_2)).willReturn(Optional.<User> of(self));

        Reviews expected = Reviews.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .self(self)
                .reviewer(reviewer)
                .rating((int) TestConstants.NUMERIC_CONSTANT_1)
                .comment(TestConstants.BODY_1)
                .build();

        // When
        Reviews response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_2);
        verify(userRepository, times(2)).findByUsernameOrEmail(any());
    }

    @Test
    public void toEntity_StatisticsShouldReturnCorrectEntity() {
        // Given
        User user = User.builder()
                .password(TestConstants.PASSWORD_1)
                .isActive(true)
                .username(TestConstants.USERNAME_1)
                .email(TestConstants.EMAIL_1)
                .role(TestConstants.ROLE_USER)
                .build();
        StatisticsDto dto = StatisticsDto.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
                .userName(TestConstants.USERNAME_1)
                .rate((int) TestConstants.NUMERIC_CONSTANT_1)
                .haircutCount((int) TestConstants.NUMERIC_CONSTANT_1)
                .followersCount((int) TestConstants.NUMERIC_CONSTANT_2)
                .build();

        given(userRepository.findByUsernameOrEmail(TestConstants.USERNAME_1)).willReturn(Optional.<User> of(user));

        Statistic expected = Statistic.builder()
                .id(1L)
                .updatedAt(LocalDateTime.MAX)
//                .user(user)
//                .rate((int) TestConstants.NUMERIC_CONSTANT_1)
//                .haircutCount((int) TestConstants.NUMERIC_CONSTANT_1)
//                .followersCount((int) TestConstants.NUMERIC_CONSTANT_2)
                .build();

        // When
        Statistic response = mapper.toEntity(dto);

        // Then

        assertThat(response)
                .isNotNull()
                .isEqualTo(expected);

        verify(userRepository, times(1)).findByUsernameOrEmail(TestConstants.USERNAME_1);
        verify(userRepository, times(1)).findByUsernameOrEmail(any());
    }

}