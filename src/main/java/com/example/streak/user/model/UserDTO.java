package com.example.streak.user.model;

import com.example.streak.user.db.enums.UserState;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDTO {
    private String name;
    private String alertTime;
    private LocalDateTime createdAt;
    private Integer workCount;
    private UserState state;
}
