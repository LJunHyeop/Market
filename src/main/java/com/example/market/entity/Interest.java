package com.example.market.entity;

//import com.example.market.entity.User;
import jakarta.persistence.*;
import com.example.market.entity.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="interest")
public class Interest {
        @EmbeddedId
        private InterestIds interestIds;

        @ManyToOne
        @MapsId("userPk")
        @JoinColumn(name="user_pk")
        private User user;

        @ManyToOne
        @MapsId("productPk")
        @JoinColumn(name="product_pk")
        private Product product;
}
