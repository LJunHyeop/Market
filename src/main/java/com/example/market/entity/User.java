package com.example.market.entity;

import com.example.market.common.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user") // 백틱으로 감싸기
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends UpdateAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPk;

    @Column
    @Comment("유저 이름")
    private String userName;

    @Column
    @Comment("유저 비밀번호")
    private String userPw;

    @Column
    @Comment("유저 이메일")
    private String userEmail;

    @Column
    @Comment("유저 or 관리자")
    private Integer userType;

    @Column(nullable = false)
    @Comment("소셜 로그인")
    private Integer userSocial;

    @Column
    @Comment("유저 매너 온도")
    private double userManner;

    @Column
    @Comment("유저 프로필 사진")
    private String pic;

    @Column
    @Comment("활성 or 벤")
    private Integer userState;

    @Column
    @Comment("핸드폰 넘버")
    private String userPhone;

    @Column
    @Comment("권한")
    private Integer userRole;
}
