package com.ieor185.user.repository;


import com.ieor185.user.model.ClefUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by xschen on 16/10/2016.
 */
@Repository
public interface ClefUserEntityRepository extends CrudRepository<ClefUserEntity, Long> {
   List<ClefUserEntity> findByUsername(String username);
   Page<ClefUserEntity> findAll(Pageable pageable);
   List<ClefUserEntity> findByEmail(String email);
}
