package com.ieor185.user.repository;


import com.ieor185.user.model.WyUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
@Repository
public interface WyUserEntityRepository extends CrudRepository<WyUserEntity, Long> {
   List<WyUserEntity> findByUsername(String username);
   Page<WyUserEntity> findAll(Pageable pageable);
   List<WyUserEntity> findByEmail(String email);
}
