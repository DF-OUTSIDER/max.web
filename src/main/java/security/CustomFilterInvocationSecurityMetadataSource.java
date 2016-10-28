package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import service.OperationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static UrlCache urlCache;
    @Autowired
    private OperationService operationService;

    private List<String> anonymousUrl = new ArrayList<>(0);

    public CustomFilterInvocationSecurityMetadataSource(List<String> anonymousUrl) {
        this.anonymousUrl = anonymousUrl;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();
        if (requestUrl != null) {
            for (String permitUrl : anonymousUrl) {
                if (requestUrl.startsWith(permitUrl)) {
                    return SecurityConfig.createList("ROLE_ANONYMOUS"); //permit anonymous role
                }
            }

            //mapper url->roles
            if (urlCache == null)
                urlCache = new UrlCache(operationService.findAllOperations());

            List<String> roleList = new ArrayList<>();
            urlCache.getUrlCaches().keySet().stream().filter(requestUrl::startsWith).forEach(key -> roleList.addAll(urlCache.getUrlCaches().get(key)));

            if (!roleList.contains("ROLE_ADMIN"))
                roleList.add("ROLE_ADMIN");

            return SecurityConfig.createList(roleList.toArray(new String[roleList.size()])); //every one needs ADMIN role
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
