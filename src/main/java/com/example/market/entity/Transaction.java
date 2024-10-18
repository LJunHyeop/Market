package com.example.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(
        name = "transaction",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_pk", "product_pk"}
                )
        }
)
public class Transaction extends UpdateAt{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long transactionPk ;

    @JoinColumn(name = "user_pk", nullable = false)
    @Comment("유저 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "product_pk", nullable = false)
    @Comment("상품 PK")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product ;
}
