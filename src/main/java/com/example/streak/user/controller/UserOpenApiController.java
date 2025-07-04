package com.example.streak.user.controller;

import com.example.streak.common.api.Api;
import com.example.streak.common.error.ErrorCode;
import com.example.streak.common.exception.ApiException;
import com.example.streak.user.db.UserEntity;
import com.example.streak.user.db.UserRepository;
import com.example.streak.user.db.enums.UserState;
import com.example.streak.user.model.*;
import com.example.streak.user.service.UserConverter;
import com.example.streak.user.service.UserService;
import com.example.streak.work.db.WorkEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.streak.utils.Encrypt.encrypt;

@Slf4j
@RestController
@RequestMapping("/open-api/user")
@RequiredArgsConstructor
public class UserOpenApiController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserConverter userConverter;

    @PostMapping("/login")
    public Api<UserDTO> login(
            @Valid
            @RequestBody
            UserLoginRequest userLoginRequest,

            HttpSession httpSession
    ){
        String encryptPassword = encrypt(userLoginRequest.getPassword());

        Optional<UserEntity> _user = userRepository.findFirstByName(userLoginRequest.getName());
        if(_user.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST,"아이디가 없습니다");
        }
        UserEntity user = _user.get();
        if(!Objects.equals(user.getPassword(), encryptPassword) && !Objects.equals(user.getTempPassword(), userLoginRequest.getPassword())){
            throw new ApiException(ErrorCode.BAD_REQUEST,"비밀번호가 틀렸습니다");
        }

        log.info(user.getTempPassword());
        log.info(encryptPassword);

        if(Objects.equals(user.getTempPassword(), userLoginRequest.getPassword())){
            String encryptTempPassword = encrypt(userLoginRequest.getPassword());
            user.setPassword(encryptTempPassword);
        }
        user.setTempPassword(null);
        user.setAlertTime("알림 안씀");
        userRepository.save(user);
        httpSession.setAttribute("USER", user.getId());

        return Api.OK(userConverter.toDTO(user));
    }

    @PostMapping("/gmail-login")
    public Api<UserDTO> gmailLogin(
            @Valid
            @RequestBody
            UserGmailLoginRequest userGmailLoginRequest,

            HttpSession httpSession
    ){
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(userGmailLoginRequest.getIdToken());
            String uid = decodedToken.getUid();
        } catch (Exception e){
            throw new ApiException(ErrorCode.BAD_REQUEST, "토큰 에러");
        }

        Optional<UserEntity> _user = userRepository.findFirstByName(userGmailLoginRequest.getName());
        UserEntity user = null;
        if(_user.isEmpty()){
            user = UserEntity.builder()
                    .createdAt(LocalDateTime.now())
                    .name(userGmailLoginRequest.getName())
                    .password("GMAIL")
                    .alertTime("알림 안씀")
                    .state(UserState.NORMAL)
                    .workCount(5).build();
        } else {
            user = _user.get();
        }
        userRepository.save(user);
        httpSession.setAttribute("USER", user.getId());

        return Api.OK(userConverter.toDTO(user));
    }

    @PostMapping("/register")
    public String register(
            @Valid
            @RequestBody
            UserRegisterRequest userRegisterRequest,

            HttpSession httpSession
    ){
        Optional<UserEntity> user = userRepository.findFirstByName(userRegisterRequest.getName());

        if(user.isPresent()){
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 있는 계정입니다.");
        } else {
            return userService.register(userRegisterRequest);
        }
    }

    @PostMapping("/password-reset")
    public String password(
            @Valid
            @RequestBody
            UserPasswordRequest userPasswordRequest,

            HttpSession httpSession
    ){
        Optional<UserEntity> user = userRepository.findFirstByName(userPasswordRequest.getEmail());

        if(user.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST,"없는 계정입니다.");
        } else {
            return userService.password(userPasswordRequest, user.get());
        }
    }
}
