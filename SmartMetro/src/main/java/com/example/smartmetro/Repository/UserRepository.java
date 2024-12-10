package com.example.smartmetro.Repository;

import com.example.smartmetro.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);

    User findUserByEmail(String email);

    @Query("select u from User u where u.preference = ?1")
    List<User> getUserByPreference(String preference);

    @Query("select u from User u where u.balance >= ?1")
    List<User> getUserByBalance(Double balance);
}
