package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Comment;
import com.kcs.stepstory.domain.CommentId;
import com.kcs.stepstory.domain.TravelReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findCommentsByTravelReportOrderByCreatedAtAsc(TravelReport travelReport);

    public Comment findCommentByCommentId(Long commentId);

    public void deleteByCommentId(Long commentId);
}
