package service;

import model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> findUsers(String name, int pageIndex, int pageSize);

    User findUserById(int id);

    User findUserByName(String name);

    Boolean hasUser();

    void saveUser(User user);

    void deleteUser(User user);
}
