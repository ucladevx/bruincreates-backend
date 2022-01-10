package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.model.user.UserControlBlock;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("ps")
public class PermissionService {

    public boolean permission(String permission) {

        UserControlBlock userControlBlock = ServletUtils.getCurrentPrincipal();
        User user = userControlBlock.getUser();
        String userPermission = user.getRole() == 0 ? "user" : "admin";

        if (userPermission.matches(permission)) {
            return true;
        }

        log.debug("userId={}, username={} access denied for [{}], user role：{}", user.getId(),
                user.getUsername(), permission, userPermission);
        return false;
    }

}
