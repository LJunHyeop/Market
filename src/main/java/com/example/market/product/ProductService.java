package com.example.market.product ;

import org.springframework.stereotype.Service;

import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.entity.User;
import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.repository.ProductPhotoRepository;
import com.example.market.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper ;
    private final ProductRepository repository ;
    private final ProductPhotoRepository photoRepository ;
    // private final UserRepository userRepository ;

    // ��ǰ ���
    public int postProduct(String token, PostProductRegistrationReq p){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;

        Product product = new Product() ;
        product.setProductName(p.getProductName()) ;
        product.setProductPrice(p.getProductPrice()) ;
        product.setProductComment(p.getProductComment()) ;
        // product.setUser(auth) ;
        repository.save(product) ;
        // product.setProductLike(p.getProductLike()) ;

        ProductPhoto photo = new ProductPhoto() ;
        photo.setProduct(product) ;
        photo.setProductPhoto(p.getProductPhoto()) ;
        // photo.setUser(auth) ;
        photoRepository.save(photo) ;

        return 1 ;
    }
}