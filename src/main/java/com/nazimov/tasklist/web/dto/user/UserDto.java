package com.nazimov.tasklist.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nazimov.tasklist.web.dto.validation.OnCreate;
import com.nazimov.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for {@link com.nazimov.tasklist.domain.user.User}
 */
@Data
public class UserDto {

    @NotNull(message = "Id cannot be null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be less than 255", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Username cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be less than 255", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation cannot be null", groups = {OnCreate.class})
    private String passwordConfirmation;

}