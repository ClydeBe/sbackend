package com.thewheel.sawatu.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.thewheel.sawatu.auth.security.SecurityConstantsBean;
import com.thewheel.sawatu.auth.security.SignatureFactory;
import com.thewheel.sawatu.auth.security.token.TokenFactory;
import com.thewheel.sawatu.core.exception.BadRequestException;
import com.thewheel.sawatu.core.mailing.EmailService;
import com.thewheel.sawatu.core.service.interfaces.UserService;
import com.thewheel.sawatu.database.model.User;
import com.thewheel.sawatu.shared.dto.RefreshTokenDto;
import com.thewheel.sawatu.shared.dto.mapper.Mapper;
import com.thewheel.sawatu.shared.dto.user.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.POST;
import static com.thewheel.sawatu.shared.constant.ApiDocumentationConstants.*;
import static com.thewheel.sawatu.shared.constant.ApiEndpointsConstants.*;
import static com.thewheel.sawatu.shared.constant.Constants.TOKEN_GRANTED_AUTHORITY_NAME;
import static com.thewheel.sawatu.shared.constant.MessageConstants.*;
import static com.thewheel.sawatu.shared.constant.SecurityConstants.SECURITY_NAME;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final UserService userService;

    private final SignatureFactory signatureFactory;

    private final UserDetailsService userDetailsService;

    private final EmailService emailService;

    private final TokenFactory tokenFactory;

    private final Mapper mapper;

    private SecurityConstantsBean securityConstantsBean;

    @ApiOperation(value = ENDPOINT_ACCOUNT_GET_USER,
            notes = ACCOUNT_GET_USER_NOTE,
            response = UserDto.class)
    @Operation(
            method = GET,
            description = ENDPOINT_ACCOUNT_GET_USER,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = 200,
            message = MESSAGE_OK,
            response = UserDto.class)
    @GetMapping(ENDPOINT_ACCOUNT_GET_USER)
    @PreAuthorize("#username == authentication.name")
    public UserDto getUserById(@PathVariable("username") String username) {
        return userService.getUser(username);
    }


    @ApiOperation(value = ACCOUNT_CREATE_USER,
            notes = ACCOUNT_CREATE_USER_NOTE,
            response = UserDto.class)
    @Operation(
            method = POST,
            description = ENDPOINT_ACCOUNT_CREATE_USER,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @ApiResponse(code = 201,
            message = MESSAGE_CREATED,
            response = UserDto.class)
    @PostMapping(ENDPOINT_ACCOUNT_REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto user) {
        String token = tokenFactory.generateAccountActivationToken(user.getUsername());
        emailService.sendActivationMessage(user, token, user.getEmail());
        emailService.sendWelcomeMessage(user.getUsername(), user.getEmail());
        return userService.create(user);
    }


    @ApiOperation(value = ACCOUNT_VERIFY_EMAIL,
            notes = ACCOUNT_VERIFY_EMAIL_NOTE,
            response = String.class)
    @ApiResponse(code = 200,
            message = MESSAGE_OK,
            response = String.class)
    @Operation(
            method = GET,
            description = ENDPOINT_ACCOUNT_VERIFY_EMAIL,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @GetMapping(ENDPOINT_ACCOUNT_VERIFY_EMAIL)
    public String verifyEmail(@RequestParam("token") Optional<String> token) {
        String username = tokenFactory.verifyAccountValidationToken(token.orElseThrow(
                () -> new BadRequestException(NO_TOKEN_PROVIDED)));
        if (username == null) throw new RuntimeException(INVALID_TOKEN);
        userService.activateAccount(username);
        return ACCOUNT_ACTIVATED;
    }


    @PutMapping(ENDPOINT_ACCOUNT_UPDATE)
    @ApiOperation(value = ACCOUNT_UPDATE_USER,
            notes = ACCOUNT_UPDATE_USER_NOTE,
            response = String.class)
    @ApiResponse(code = 200,
            message = MESSAGE_OK,
            response = User.class)
    @Operation(
            method = PUT,
            description = ACCOUNT_UPDATE_USER,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    @PreAuthorize("#user.username == authentication.name")
    public UserDto updateUser(@Valid @RequestBody UserDto user) throws BadRequestException {
        return userService.merge(user);
    }


    @PostMapping(ENDPOINT_ACCOUNT_REFRESH_TOKEN)
    @ApiOperation(value = ACCOUNT_REFRESH_TOKEN,
            notes = ACCOUNT_REFRESH_TOKEN_NOTE,
            response = String.class)
    @ApiResponse(code = 200,
            message = MESSAGE_OK,
            response = Map.class)
    @Operation(
            method = POST,
            description = ACCOUNT_UPDATE_USER,
            security = @SecurityRequirement(name = SECURITY_NAME)
    )
    public Map<String, String> refreshToken(HttpServletRequest request, @RequestBody RefreshTokenDto token) {
        String subject = JWT.decode(token.getRefreshToken()).getSubject();
        Algorithm algorithm = Algorithm.HMAC512(signatureFactory.getSignature(subject));
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        String accessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(securityConstantsBean.getIssuer())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(
                        System.currentTimeMillis() + securityConstantsBean.getAccessTokenDuration() * 60 * 1000))
                .withClaim(TOKEN_GRANTED_AUTHORITY_NAME, userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        return tokens;
    }
}



