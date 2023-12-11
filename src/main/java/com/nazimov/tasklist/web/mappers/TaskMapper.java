package com.nazimov.tasklist.web.mappers;

import com.nazimov.tasklist.domain.task.Task;
import com.nazimov.tasklist.web.dto.task.TaskDto;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper extends Mapper<Task, TaskDto>  {

}