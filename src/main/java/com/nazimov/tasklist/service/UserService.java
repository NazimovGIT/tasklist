package com.nazimov.tasklist.service;

import com.nazimov.tasklist.domain.user.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    boolean isTaskOwner(Long taskId, Long userId);

    User update(User user);

    User create(User user);

    void delete(Long id);

}
