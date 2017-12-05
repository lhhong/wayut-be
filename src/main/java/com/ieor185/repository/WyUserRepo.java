package com.ieor185.repository;


import com.ieor185.model.WyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WyUserRepo extends CrudRepository<WyUser, String> {
}
