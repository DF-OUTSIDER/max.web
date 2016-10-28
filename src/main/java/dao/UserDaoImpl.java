package dao;

import common.PaginationData;
import model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public PaginationData<User> findUsers(String name, int pageIndex, int pageSize) {
        List<User> users;
        Number resultCount;
        if (StringUtils.hasText(name)) {
            users = sessionFactory
                    .getCurrentSession()
                    .createQuery("from User where name like :name order by id")
                    .setParameter("name", String.format("%%%s%%", name))
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
            resultCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery(" from User where name like :name")
                    .setParameter("name", String.format("%%%s%%", name))
                    .getSingleResult();

        } else {
            users = sessionFactory
                    .getCurrentSession()
                    .createQuery("from User order by id")
                    .setFirstResult((pageIndex - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
            resultCount = (Number) sessionFactory
                    .getCurrentSession()
                    .createQuery("select count (id) from User")
                    .getSingleResult();
        }
        return new PaginationData<User>(users, resultCount.intValue());
    }

    @Override
    public User findUserById(int id) {
        return (User) sessionFactory
                .getCurrentSession()
                .createQuery("from User where id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUserByName(String username) {
        List<User> results = sessionFactory
                .getCurrentSession()
                .createQuery("from User where name=:name")
                .setParameter("name", username)
                .getResultList();
        if (results == null || results.size() <= 0)
            return null;
        else
            return results.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean hasUser() {
        List<User> users = sessionFactory
                .getCurrentSession()
                .createQuery("from User")
                .setMaxResults(1)
                .getResultList();
        if (users.size() > 0)
            return true;
        return false;
    }

    @Override
    public void saveUser(User user) {
        sessionFactory
                .getCurrentSession()
                .save(user);
    }

    @Override
    public void updateUser(User user) {
        sessionFactory
                .getCurrentSession()
                .update(user);
    }

    @Override
    public void deleteUser(User user) {
        sessionFactory
                .getCurrentSession()
                .delete(user);
    }
}
