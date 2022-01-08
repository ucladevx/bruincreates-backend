package com.bruincreates.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("ps")
public class PermissionService {

    public boolean permission(String permission) {
        return true;
    }

}
