package com.example.market.product ;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.market.common.CustomFileUtils;
import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.model.UpdateProductReq;
import com.example.market.product.repository.ProductPhotoRepository;
import com.example.market.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 상품 삭제
    @Transactional
    public int delProduct(String token, Long pk){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        
        Product product = new Product() ;
        repository.findById(pk) ;
        
        List<ProductPhoto> productPhoto = new ArrayList<>() ;
        photoRepository.findAllByProduct(product) ;
        try{
            // 경로 설정 및 삭제 처리
            String path = String.format("photo/%d", product.getProductPk()) ;

            // 관련 ProductPhoto 데이터베이스 삭제
            photoRepository.deleteAll(productPhoto) ;

            // // 사진 개별 삭제
            // for(ProductPhoto photo : productPhoto){
            //     String filePath = String.format("%s/%s", path, photo.getProductPhoto()) ;
            //     File file = new File(filePath) ;
            //     if(file.exists()){
            //         file.delete() ;
            //     }
            // }
            // 전체 폴더 삭제
            customFileUtils.deleteFolder(path) ;
        } catch(Exception e){
            e.printStackTrace() ;
            throw new RuntimeException("사진 삭제 실패.") ;
        }
        
        repository.delete(product) ;
        return 1 ;
    }

    @Transactional
    public int updateProduct(String token, UpdateProductReq p, List<MultipartFile> pics){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;


        // Optional로 Product 조회 및 예외 처리
        Product product = repository.findById(p.getProductPk())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        // Product 업데이트
        product.setProductName(p.getProductName());
        product.setProductPrice(p.getProductPrice());
        product.setProductComment(p.getProductComment());
        // product.setUser(auth);

        // Product 저장
        repository.save(product);

        List<String> picName = new ArrayList<>() ;
 
        List<ProductPhoto> deletePhotoList = photoRepository.findAllByProduct(product) ;
        photoRepository.deleteAll(deletePhotoList) ;

        try{
            List<ProductPhoto> picsList = new ArrayList<>() ;
            // 파일 저장 경로 설정 및 폴더 생성
            String path = String.format("photo/%d", product.getProductPk()) ;
            customFileUtils.deleteFolder(path) ;
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