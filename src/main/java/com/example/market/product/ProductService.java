package com.example.market.product ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.market.common.CustomFileUtils;
import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.product.model.GetProduct;
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

    // 전체 검색, 검색어 검색
    public List<GetProduct> getAllProduct(String token, String p, int page, int size){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        
        Pageable pageable = PageRequest.of(page, size) ;
        Page<Product> productsPage ;

        if (p == null || p.isEmpty()) {
            // 전체 검색
            productsPage = repository.findAll(pageable) ;
        } else {
            // 검색어로 검색
            productsPage = repository.findProductsByProduct(p, pageable) ;
        }

        // Page<Product> 결과를 List<GetProduct>로 변환
        List<GetProduct> productList = productsPage.stream()
            .map(product -> new GetProduct(product)) // Product를 GetProduct로 변환하는 로직
            .collect(Collectors.toList()) ;

        return productList ;
    }

    // 상품 상세조회
    public Product getSelectProduct(String token, long p){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        Product product = new Product() ;
        repository.findById(p) ;
        return product ;
    }

    // 상품 사진 조회
    public List<ProductPhoto> getSelectPhoto(String token, long p){
        // Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        // Product 객체 가져오기
        Product product = repository.findById(p).orElse(null);
        if (product == null) {
            throw new RuntimeException("상품이 존재하지 않습니다.") ;
        }

        // 사진 목록 가져오기
        List<ProductPhoto> photos = photoRepository.findAllByProduct(product) ;
        List<String> picNames = new ArrayList<>() ;
        
        for (ProductPhoto pic : photos) {
            picNames.add(pic.getProductPhoto()) ; // getFileName() 메서드는 실제 ProductPhoto 클래스에서 파일명 필드의 getter로 변경
        }
        
        return photos ;
    }

    // 전체 항목 수를 반환하는 메서드
    public long getTotalCount(String p) {
        if (p == null || p.isEmpty()) {
            return repository.count() ;
        } else {
            return repository.countByProductNameOrProductComment(p, p) ; // 검색어로 필터링된 항목 수 반환
        }
    }
}