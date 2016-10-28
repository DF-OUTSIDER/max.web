package dao;

import common.PaginationData;
import model.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAllRoles() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Role order by id")
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaginationData<Role> findRoles(String roleName, int pageIndex, int pageSize) {
        List<Role> roles;
        Number totalRoleCount;
        if (StringUtils.hasText(roleName)) {
            roles = sessionFactory
                    .getCurrentSession()
                    .createQuery("from Role r where r.name like :roleName order by r.id")
                    .setParameter("roleName", String.format("%%%s%%", roleName))
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
            totalRoleCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery("select count(r.id) from Role r where r.name like :roleName")
                    .setParameter("roleName", String.format("%%%s%%", roleName))
                    .getSingleResult();
        } else {
            roles = sessionFactory
                    .getCurrentSession()
                    .createQuery("from Role r order by r.id")
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
            totalRoleCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery("select count(r.id) from Role r")
                    .getSingleResult();
        }
        return new PaginationData<Role>(roles, totalRoleCount.intValue());
    }

    @Override
    public Role findRoleById(int id) {
        return (Role) sessionFactory
                .getCurrentSession()
                .createQuery("from Role r where r.id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void saveRole(Role role) {
        sessionFactory
                .getCurrentSession()
                .save(role);
    }

    @Override
    public void updateRole(Role role) {
        sessionFactory
                .getCurrentSession()
                .update(role);
    }

    @Override
    public void deleteRole(Role role) {
        sessionFactory
                .getCurrentSession()
                .delete(role);
    }
}
