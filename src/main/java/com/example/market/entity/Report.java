package com.example.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report extends UpdateAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("신고 Pk")
    private int reportPk;

    @JoinColumn(name = "user_pk")
    @ManyToOne
    @Comment("신고한 유저")
    private User userPk;

    @Column
    @Comment("신고내용")
    private String reportMessage;

    @Column
    @Comment("신고 타입 유저 or 게시물")
    private int reportType;

    @Column
    @Comment("유저 또는 게시물 Pk")
    private int reportTarget;
}
