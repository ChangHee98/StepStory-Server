package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.DetailCourse;
import com.kcs.stepstory.domain.DetailCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailCourseRepository extends JpaRepository<DetailCourse, DetailCourseId> {

    DetailCourse findByDetailCourseId(Long detailCourseId);
}
