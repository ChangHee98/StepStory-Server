package com.kcs.stepstory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "Friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="friendId", nullable = false)
    private Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId1", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId2", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user2;

    @Column(name = "status", nullable = false)
    private int status;

    @Builder
    public Friend(User user1, User user2, int status){
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }
}
