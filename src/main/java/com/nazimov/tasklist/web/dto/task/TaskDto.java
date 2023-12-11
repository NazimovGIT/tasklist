package com.nazimov.tasklist.web.dto.task;

import com.nazimov.tasklist.domain.task.Status;
import com.nazimov.tasklist.web.dto.validation.OnCreate;
import com.nazimov.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * DTO for {@link com.nazimov.tasklist.domain.task.Task}
 */
@Data
public class TaskDto {

    @NotNull(message = "Id cannot be null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be less than 255", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "Description length must be less than 255", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private LocalDateTime expirationDate;

}