package com.mystudies.springboot2essentials.repository;

import com.mystudies.springboot2essentials.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDetailsRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
