package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.DetailCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailCourseRepository extends JpaRepository<DetailCourse, DetailCourseId> {
}
