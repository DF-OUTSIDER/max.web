package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUsers(String name, int pageIndex, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
        if (StringUtils.hasText(name))
            return userRepository.findUserByNameContains(name, pageable);
        else
            return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByName(String name) {
        return userRepository.findFirstUserByNameEquals(name);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasUser() {
        return userRepository.count() > 0;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
