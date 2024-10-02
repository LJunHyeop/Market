package com.example.market.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Table(name = "location")
public class Location  extends  UpdateAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationPk;

    @JoinColumn
    @Comment("유저 Pk")
    @ManyToOne
    private User userPk;

    @Column
    @Comment("위도")
    private double latitude;

    @Column
    @Comment("경도")
    private double longitude;

    @Column
    @Comment("행정 구역")
    private String administrativeDistrict;

}
