package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.WantToGo;
import com.kcs.stepstory.domain.WantToGoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantToGoRepository extends JpaRepository<WantToGo, WantToGoId> {
}
