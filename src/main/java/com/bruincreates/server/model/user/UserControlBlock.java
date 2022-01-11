package com.bruincreates.server.model.user;

import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.model.security.SecurityFlag;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * UserControlBlock is used for Spring Security framework
 * to automatically discover bad credential and fallback
 * to the corresponding authentication handler as described
 * in [WebSecurityConfig]
 *
 * UserControlBlock is not your typical understanding of
 * data access object (DAO) or view object (VO), and it
 * should not be used for ordinary business processing
 *
 * @author aojiao
 */

@Data
public class UserControlBlock implements UserDetails, CredentialsContainer {

    private static final long serialVersionUid = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private User user;

    private String loginIp;

    private LocalDateTime loginTime;

    public UserControlBlock() { }

    public UserControlBlock(User user, String loginIp, LocalDateTime loginTime) {
        this.user = user;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }

    @Override
    public void eraseCredentials() {
        user.setPassword(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked() == SecurityFlag.UN_LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getDisabled() == SecurityFlag.ENABLED;
    }

}
