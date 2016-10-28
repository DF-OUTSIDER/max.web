package dao;

import common.PaginationData;
import model.User;

public interface UserDao {
    PaginationData<User> findUsers(String name, int pageIndex, int pageSize);

    User findUserById(int id);

    User findUserByName(String username);

    boolean hasUser();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
