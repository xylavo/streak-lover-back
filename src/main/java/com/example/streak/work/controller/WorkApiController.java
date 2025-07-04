package com.example.streak.work.controller;

import com.example.streak.common.api.Api;
import com.example.streak.common.error.ErrorCode;
import com.example.streak.common.exception.ApiException;
import com.example.streak.streak.db.StreakEntity;
import com.example.streak.user.db.UserEntity;
import com.example.streak.user.db.UserRepository;
import com.example.streak.work.db.WorkEntity;
import com.example.streak.work.model.*;
import com.example.streak.work.service.WorkService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/work")
@RequiredArgsConstructor
public class WorkApiController {

    private final UserRepository userRepository;
    private final WorkService workService;

    @PostMapping("/extend")
    private List<WorkDTO> extend(
            @Valid
            @RequestBody
            WorkExtendRequest workExtendRequest,

            HttpSession httpSession
    ) throws Exception {
        log.info("===");
        log.info(String.valueOf(workExtendRequest.getId()));
        log.info("===");
        Long id = (Long)(httpSession.getAttribute("USER"));
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        return workService.extendStreak(id, workExtendRequest.getId());
    }

    @PostMapping("/register")
    private String register(
            @Valid
            @RequestBody
            WorkRegisterRequest workRegisterRequest,

            HttpSession httpSession
    ) throws Exception {
        Long id = (Long)(httpSession.getAttribute("USER"));
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        return workService.register(id, workRegisterRequest);
    }

    @GetMapping("/{streakId}")
    public Api<WorkEntity> getStreak(
            @PathVariable Long streakId,
            HttpSession httpSession
    ) {
        Long id = (Long)(httpSession.getAttribute("USER"));
        log.info("id: {}", id);
        Optional<UserEntity> userEntity = userRepository.findById(id);
        log.info("user: {}", userEntity.get());
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        return Api.OK(workService.find(userEntity.get().getId(), streakId));
    }

    @PostMapping("/delete")
    public void delete(
            @Valid
            @RequestBody
            WorkDeleteRequest workDeleteRequest,
            HttpSession httpSession
    ){
        Long id = (Long)(httpSession.getAttribute("USER"));
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        workService.delete(userEntity.get().getId(), workDeleteRequest.getId());
    }

    @PostMapping("/repair")
    public void repair(
            @Valid
            @RequestBody
            WorkRepairRequest workRepairRequest,
            HttpSession httpSession
    ){
        Long id = (Long)(httpSession.getAttribute("USER"));
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        workService.repair(userEntity.get(), workRepairRequest.getId());
    }

    @PostMapping("/repair-buy")
    public void repairBuy(
            @Valid
            @RequestBody
            WorkRepairRequest workRepairRequest,
            HttpSession httpSession
    ){
        Long id = (Long)(httpSession.getAttribute("USER"));
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new ApiException(ErrorCode.UNAUTHORIZED);

        workService.repairBuy(userEntity.get(), workRepairRequest.getId());
    }
}
