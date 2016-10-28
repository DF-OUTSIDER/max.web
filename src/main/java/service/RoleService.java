package service;

import common.PaginationData;
import model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();

    PaginationData<Role> findRoles(String roleName, int pageIndex, int pageSize);

    Role findRoleById(int id);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRole(Role role);
}
