package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Comment;
import com.kcs.stepstory.domain.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
