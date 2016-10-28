package security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomAffirmativeBased extends AbstractAccessDecisionManager {

    protected CustomAffirmativeBased(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        int deny = 0;
        for (Object o1 : this.getDecisionVoters()) {
            AccessDecisionVoter voter = (AccessDecisionVoter) o1;
            int result = voter.vote(authentication, o, collection);
            switch (result) {
                case -1:
                    ++deny;
                    break;
                case 1:
                    return;
            }
        }

        if (deny > 0)
            throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        else
            this.checkAllowIfAllAbstainDecisions();
    }
}
