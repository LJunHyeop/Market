package com.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "report")
public class report extends UpdateAt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("신고 Pk")
    private int reportPk;

    @JoinColumn
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
