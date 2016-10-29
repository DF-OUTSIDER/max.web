package security;

import model.Role;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.equals("admin") && !userService.hasUser()) { //first admin user. should check in database
            user = new User();
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            user.setRoles((new ArrayList<>((Arrays.asList(role)))));
            user.setEnabled(true);
            user.setLastLoginTime((new Date()));
        } else {
            user = userService.findUserByName(username);
        }
        if (user == null)
            throw new UsernameNotFoundException("No User Found");
        return buildUserForAuthentication(user, buildUserAuthentication(user.getRoles()));
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> grantedAuthorityList) {
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getEnabled(), true, true, true, grantedAuthorityList);
    }

    private List<GrantedAuthority> buildUserAuthentication(List<Role> roles) {
        Set<GrantedAuthority> grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        return new ArrayList<>(grantedAuthorities);
    }
}
