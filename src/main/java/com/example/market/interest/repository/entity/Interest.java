package com.example.market.interest.repository.entity;

import com.example.market.entity.User;
import jakarta.persistence.*;
import com.example.market.entity.*;


@Entity
@Table( // 복합 유니크
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"user_pk", "product_pk"}
                )
        }
)
public class Interest {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long InterestPk;

        @ManyToOne
        @JoinColumn(name="user_pk", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name="product_pk", nullable = false)
        private Product product;

}
