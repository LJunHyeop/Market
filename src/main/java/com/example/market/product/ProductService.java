package com.example.market.product ;

import org.springframework.stereotype.Service;

import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.entity.User;

import org.springframework.web.multipart.MultipartFile;

import com.example.market.common.CustomFileUtils;
import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.repository.ProductPhotoRepository;
import com.example.market.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.ArrayList;
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper mapper ;
    private final ProductRepository repository ;
    private final ProductPhotoRepository photoRepository ;
    private final CustomFileUtils customFileUtils ;
    // private final UserRepository userRepository ;

    // 상품 등록
    @Transactional
    public int postProduct(String token, PostProductRegistrationReq p, List<MultipartFile> pics){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;

        Product product = new Product() ;
        product.setProductName(p.getProductName()) ;
        product.setProductPrice(p.getProductPrice()) ;
        product.setProductComment(p.getProductComment()) ;
        // product.setUser(auth) ;
        repository.save(product) ;
        // product.setProductLike(p.getProductLike()) ;


        List<String> picName = new ArrayList<>() ;

        try{
            List<ProductPhoto> picsList = new ArrayList<>() ;
            // 파일 저장 경로 설정 및 폴더 생성
            String path = String.format("photo/%d", product.getProductPk()) ;
            customFileUtils.makeFolders(path) ;

            for(MultipartFile pic : pics){
                // 랜덤 파일 이름 생성 및 파일 저장
                String saveFileName = customFileUtils.makeRandomFileName(pic) ;
                String target = String.format("%s/%s", path, saveFileName) ;
                customFileUtils.transferTo(pic, target) ;

                picName.add(saveFileName) ;

                ProductPhoto productPhoto = new ProductPhoto() ;
                productPhoto.setProductPhoto(saveFileName) ;
                productPhoto.setProduct(product) ;

                picsList.add(productPhoto) ;
                photoRepository.save(productPhoto) ;
            }
        } catch(Exception e) {
            e.printStackTrace() ;
            throw new RuntimeException("상품 등록 오류") ;
        }

        return 1 ;
    }
}