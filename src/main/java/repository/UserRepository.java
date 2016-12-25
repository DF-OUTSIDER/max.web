package repository;

import model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.name like %:name%")
    Page<User> findUsers(@Param("name") String name, Pageable pageable);

    @Query("select u from User u where u.name = :username")
    User findUserByName(@Param("username") String username);

    @Query("select count (u.id) from User u")
    Long findIdCount();
}
