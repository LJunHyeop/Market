package com.example.market.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "manner")
// 매너 평가 항목
public class Manner {
    @Id
    private Long mannerId;

    @Column(nullable = false)
    private String sentence;

    @Column(nullable = false)
    @Comment("1: 매너, 2: 비매너")
    private Integer type;
}
