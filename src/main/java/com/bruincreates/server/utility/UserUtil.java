package com.bruincreates.server.utility;

import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.model.user.UserControlBlock;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static User getRuntimeUser() {
        UserControlBlock userControlBlock = (UserControlBlock) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userControlBlock.getUser();
    }
}
