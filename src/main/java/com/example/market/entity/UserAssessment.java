package com.example.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@Table( // 복합 유니크
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"user_id", "manner_id"}
                )
        }
)
public class UserAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserAssPk;

    @JoinColumn(name="user_id", nullable = false)
    @ManyToOne
    @Comment("유저 PK")
    private User user;

    @JoinColumn(name="manner_id", nullable = false)
    @ManyToOne
    @Comment("평가 항목 PK")
    private Manner manner;

    @Column
    @Comment("횟수")
    private Long count;
}
