package security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class CustomRoleVoter implements AccessDecisionVoter<Object> {
    private String rolePrefix = "ROLE_";

    public String getRolePrefix() {
        return rolePrefix;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return configAttribute.getAttribute() != null && configAttribute.getAttribute().startsWith(this.getRolePrefix());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object o, Collection<ConfigAttribute> collection) {
        if (authentication == null)
            return -1;
        else {
            Collection authorities = this.extractAuthorities(authentication);
            Iterator configAttributeIterator = collection.iterator();
            while (true) {
                ConfigAttribute configAttribute;
                do {
                    if (!configAttributeIterator.hasNext()) {
                        return 0;
                    }
                    configAttribute = (ConfigAttribute) configAttributeIterator.next();
                } while (!this.supports(configAttribute));

                for (Object authority : authorities) {
                    GrantedAuthority grantedAuthority = (GrantedAuthority) authority;
                    if (grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
                        return 1;
                    if (configAttribute.getAttribute().equals(grantedAuthority.getAuthority()))
                        return 1;
                }
            }
        }
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
