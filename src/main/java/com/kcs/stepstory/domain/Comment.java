package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@IdClass(CommentId.class)
@Table(name = "Comment")
public class Comment {
    @Id
    @Column(name = "commentId",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReportId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TravelReport travelReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "content",nullable = false)
    private String content;
    @Column(name = "parentCommentId",nullable = true)
    private long parentCommentId;
    @CreationTimestamp
    @Column(name = "createdAt",nullable = false)
    private Timestamp createdAt;



    @Builder
    public Comment(TravelReport travelReport, User user, String content, long parentCommentId){
        this.travelReport = travelReport;
        this.user = user;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public void updateComment(String content){this.content = content;}
}
