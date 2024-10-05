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
                        columnNames={"user_pk", "manner_id"}
                )
        }
)
public class UserAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserAssPk;

//    그 테이블의 컬럼명과 일치 해야 함
    @JoinColumn(name="user_pk", nullable = false)
    @ManyToOne
    @Comment("유저 PK")
    private User user;

    @JoinColumn(name="manner_id", nullable = false)
    @ManyToOne
    @Comment("평가 항목 PK")
    private Manner manner;

    @Column(name="count", nullable = false)
    @Comment("횟수")
    private Integer count;
}
