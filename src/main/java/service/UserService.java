package service;

import common.PaginationData;
import model.User;

public interface UserService {
    PaginationData<User> findUsers(String name, int pageIndex, int pageSize);

    User findUserById(int id);

    User findUserByName(String name);

    Boolean hasUser();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
