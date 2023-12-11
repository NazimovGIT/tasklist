package com.nazimov.tasklist.web.mappers;

import com.nazimov.tasklist.domain.user.User;
import com.nazimov.tasklist.web.dto.user.UserDto;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper extends Mapper<User, UserDto> {

}