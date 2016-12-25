package service;

import model.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    Page<Role> findRoles(String roleName, int pageIndex, int pageSize);

    Role findRoleById(int id);

    void saveRole(Role role);

    void deleteRole(Role role);
}
