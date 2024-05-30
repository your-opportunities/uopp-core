package ed.uopp.uoppcore.security.data;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    MODERATOR, DEFAULT;

    @Override
    public String getAuthority() {
        return name();
    }
}
