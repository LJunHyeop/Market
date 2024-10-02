package com.example.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "manner")
public class Manner {
    @Id
    private Long mannerId;

    @Column
    private String sentence;

    @Column
    @Comment("1: 매너, 2: 비매너")
    private Integer type;
}
