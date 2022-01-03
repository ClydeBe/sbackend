package com.thewheel.sawatu.shared.dto.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thewheel.sawatu.constants.MessageConstants;
import com.thewheel.sawatu.database.model.*;
import com.thewheel.sawatu.database.repository.PostRepository;
import com.thewheel.sawatu.database.repository.UserRepository;
import com.thewheel.sawatu.shared.dto.*;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import com.thewheel.sawatu.shared.exception.BadRequestException;
import com.thewheel.sawatu.shared.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final ObjectMapper objectMapper;

    public AppointmentDto fromEntity(Appointment appointment) {
        if (appointment == null) return null;
        AppointmentDto appointmentDto = AppointmentDto.builder()
                .id(appointment.getId())
                .client(fromEntity(appointment.getClient()))
                .vendor(fromEntity(appointment.getVendor()))
                .haircut(fromEntity(appointment.getHaircut()))
                .haircutOrder(fromEntity(appointment.getHaircutOrder()))
                .dateTime(appointment.getDateTime())
                .isVendorAddress(appointment.isVendorAddress())
                .updatedAt(appointment.getUpdatedAt())
                .build();
        return appointmentDto;
    }

    public Appointment toEntity(AppointmentDto appointmentDto) {
        if (appointmentDto == null) return null;
        Appointment appointment = Appointment.builder()
                .id(appointmentDto.getId())
                .client(loadUser(appointmentDto.getClientName()))
                .vendor(loadUser(appointmentDto.getVendorName()))
                .dateTime(appointmentDto.getDateTime())
                .haircut(toEntity(appointmentDto.getHaircut()))
                .haircutOrder(toEntity(appointmentDto.getHaircutOrder()))
                .isVendorAddress(appointmentDto.isVendorAddress())
                .updatedAt(appointmentDto.getUpdatedAt())
                .build();
        return appointment;
    }

    public AvailabilityDto fromEntity(Availability availability) {
        if (availability == null) return null;
        return AvailabilityDto.builder()
                .id(availability.getId())
                .updatedAt(availability.getUpdatedAt())
                .user(fromEntity(availability.getUser()))
                .availabilities(stringAsPeriods(availability.getAvailabilities()))
                .build();
    }

    public Availability toEntity(AvailabilityDto availabilityDto) {
        if (availabilityDto == null) return null;
        return Availability.builder()
                .id(availabilityDto.getId())
                .availabilities(listAsString(
                        availabilityDto.getAvailabilities()
                                .stream()
                                .filter(dto -> dto.getDay().isAfter(LocalDate.now()))
                                .collect(Collectors.toList())))
                .updatedAt(availabilityDto.getUpdatedAt())
                .user(loadUser(availabilityDto.getUserName()))
                .build();
    }

    public CommentDto fromEntity(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .id(comment.getId())
                .author(fromEntity(comment.getAuthor()))
                .body(comment.getBody())
                .postId(comment.getPostId())
                .replyId(comment.getReplyId())
                .updatedAt(comment.getUpdatedAt())
                .comments(comment.getComments()
                                  .stream()
                                  .map(this::fromEntity)
                                  .collect(Collectors.toList()))
                .build();
    }

    public Comment toEntity(CommentDto commentDto) {
        if (commentDto == null) return null;
        return Comment.builder()
                .id(commentDto.getId())
                .author(loadUser(commentDto.getAuthorName()))
                .body(commentDto.getBody())
                .post(postRepository.findById(commentDto.getPostId()).orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(MessageConstants.ENTITY_NOT_FOUND,
                                              MessageConstants.POST,
                                              commentDto.getPostId()))))
                .replyId(commentDto.getReplyId())
                .updatedAt(commentDto.getUpdatedAt())
                .build();
    }

    public HaircutDto fromEntity(Haircut haircut) {
        if (haircut == null) return null;
        return HaircutDto.builder()
                .id(haircut.getId())
                .duration(haircut.getDuration())
                .label(haircut.getLabel())
                .photo(haircut.getPhoto())
                .price(haircut.getPrice())
                .updatedAt(haircut.getUpdatedAt())
                .vatRatio(haircut.getVatRatio())
                .vendor(fromEntity(haircut.getVendor()))
                .build();
    }

    public Haircut toEntity(HaircutDto haircutDto) {
        if (haircutDto == null) return null;
        return Haircut.builder()
                .id(haircutDto.getId())
                .duration(haircutDto.getDuration())
                .label(haircutDto.getLabel())
                .photo(haircutDto.getPhoto())
                .price(haircutDto.getPrice())
                .updatedAt(haircutDto.getUpdatedAt())
                .vatRatio(haircutDto.getVatRatio())
                .vendor(loadUser(haircutDto.getVendorName()))
                .build();
    }

    public HaircutOrderDto fromEntity(HaircutOrder haircutOrder) {
        if (haircutOrder == null) return null;
        return HaircutOrderDto.builder()
                .id(haircutOrder.getId())
                .haircut(fromEntity(haircutOrder.getHaircut()))
                .description(haircutOrder.getDescription())
                .date(haircutOrder.getDate())
                .price(haircutOrder.getPrice())
                .updatedAt(haircutOrder.getUpdatedAt())
                .client(fromEntity(haircutOrder.getClient()))
                .appointments(haircutOrder.getAppointments()
                                      .stream()
                                      .map(this::fromEntity)
                                      .collect(Collectors.toList()))
                .build();
    }

    public HaircutOrder toEntity(HaircutOrderDto haircutOrderDto) {
        if (haircutOrderDto == null) return null;
        return HaircutOrder.builder()
                .id(haircutOrderDto.getId())
                .haircut(toEntity(haircutOrderDto.getHaircut()))
                .description(haircutOrderDto.getDescription())
                .date(haircutOrderDto.getDate())
                .price(haircutOrderDto.getPrice())
                .updatedAt(haircutOrderDto.getUpdatedAt())
                .client(loadUser(haircutOrderDto.getClientName()))
                .build();
    }

    public PostDto fromEntity(Post post) {
        if (post == null) return null;
        return PostDto.builder()
                .id(post.getId())
                .body(post.getBody())
                .cover(post.getCover())
                .tags(post.getTags())
                .updatedAt(post.getUpdatedAt())
                .author(fromEntity(post.getAuthor()))
                .title(post.getTitle())
                .build();
    }

    public Post toEntity(PostDto postDto) {
        if (postDto == null) return null;
        return Post.builder()
                .id(postDto.getId())
                .body(postDto.getBody())
                .cover(postDto.getCover())
                .tags(postDto.getTags())
                .updatedAt(postDto.getUpdatedAt())
                .author(loadUser(postDto.getAuthorName()))
                .title(postDto.getTitle())
                .build();
    }

    public ProductDto fromEntity(Product product) {
        if (product == null) return null;
        return ProductDto.builder()
                .id(product.getId())
                .characteristics(product.getCharacteristics())
                .description(product.getDescription())
                .image(product.getImage())
                .label(product.getLabel())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .updatedAt(product.getUpdatedAt())
                .usage(product.getUsage())
                .vendor(fromEntity(product.getVendor()))
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        if (productDto == null) return null;
        return Product.builder()
                .id(productDto.getId())
                .characteristics(productDto.getCharacteristics())
                .description(productDto.getDescription())
                .image(productDto.getImage())
                .label(productDto.getLabel())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .updatedAt(productDto.getUpdatedAt())
                .usage(productDto.getUsage())
                .vendor(loadUser(productDto.getVendorName()))
                .build();
    }

    public ProductOrderDto fromEntity(ProductOrder productOrder) {
        if (productOrder == null) return null;
        return ProductOrderDto.builder()
                .id(productOrder.getId())
                .items(stringAsMap(productOrder.getItems()))
                .updatedAt(productOrder.getUpdatedAt())
                .user(fromEntity(productOrder.getUser()))
                .build();
    }

    public ProductOrder toEntity(ProductOrderDto productOrderDto) {
        if (productOrderDto == null) return null;
        return ProductOrder.builder()
                .id(productOrderDto.getId())
                .items(mapAsString(productOrderDto.getItems()))
                .updatedAt(productOrderDto.getUpdatedAt())
                .user(loadUser(productOrderDto.getUserName()))
                .build();
    }

    public ReviewDto fromEntity(Reviews reviews) {
        if (reviews == null) return null;
        return ReviewDto.builder()
                .id(reviews.getId())
                .comment(reviews.getComment())
                .rating(reviews.getRating())
                .reviewer(fromEntity(reviews.getReviewer()))
                .self(fromEntity(reviews.getSelf()))
                .updatedAt(reviews.getUpdatedAt())
                .build();
    }

    public Reviews toEntity(ReviewDto reviewDto) {
        if (reviewDto == null) return null;
        return Reviews.builder()
                .id(reviewDto.getId())
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .reviewer(loadUser(reviewDto.getReviewerName()))
                .self(loadUser(reviewDto.getSelfName()))
                .updatedAt(reviewDto.getUpdatedAt())
                .build();
    }

    public ProfileDto fromEntity(Profile profile) {
        if(profile == null) return null;
        return ProfileDto.builder()
                .userName(profile.getUserName())
                .followersCount(profile.getFollowersCount())
                .haircutCount(profile.getHaircutCount())
                .user(fromEntity(profile.getUser()))
                .rate(profile.getRate())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    public UserDto fromEntity(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .address(user.getAddress())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .isActive(user.getIsActive())
                .photo(user.getPhoto())
                .role(user.getRole())
                .status(user.getStatus())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) return null;
        return User.builder()
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .username(userDto.getUsername())
                .isActive(userDto.getIsActive())
                .photo(userDto.getPhoto())
                .role(userDto.getRole())
                .status(userDto.getStatus())
                .updatedAt(userDto.getUpdatedAt())
                .password(userDto.getPassword())
                .build();
    }

    private String listAsString(List<PeriodDto> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Unable to parse periods");
        }
    }

    private List<PeriodDto> stringAsPeriods(String period) {
        try {
            return objectMapper.readValue(period, List.class);
        } catch (JsonProcessingException e) {
            throw new ServerException("Unable to parse data");
        }
    }

    private String mapAsString(Map<ProductDto, Integer> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Unable to parse products");
        }
    }

    private Map<ProductDto, Integer> stringAsMap(String entity) {
        try {
            return objectMapper.readValue(entity, Map.class);
        } catch (JsonProcessingException e) {
            throw new ServerException("Unable to serialize products");
        }
    }

    private User loadUser(String username) {
        return userRepository.findByUsernameOrEmail(username).orElseThrow(
                () -> new EntityNotFoundException(String.format(MessageConstants.USER_NOT_FOUND, username)));
    }

}
