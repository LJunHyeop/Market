package com.example.market.product ;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.market.common.CustomFileUtils;
import com.example.market.entity.Product;
import com.example.market.entity.ProductLike;
import com.example.market.entity.ProductPhoto;
import com.example.market.entity.User;
import com.example.market.jwt.JwtTokenProvider;
import com.example.market.product.model.GetProduct;
import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.model.UpdateProductReq;
import com.example.market.product.model.UserFindRes;
import com.example.market.product.repository.ProductLikeRepository;
import com.example.market.product.repository.ProductPhotoRepository;
import com.example.market.product.repository.ProductRepository;
import com.example.market.security.MyUserDetail;
import com.example.market.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository ;
    private final JwtTokenProvider jwtTokenProvider ;
    private final ProductLikeRepository likeRepository ;

    // 상품 등록
    @Transactional
    public int postProduct(String token, PostProductRegistrationReq p, List<MultipartFile> pics){


        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;

        User user = userRepository.getReferenceById(userPk) ;

        log.info("user: {}", user) ;
        System.out.println(user) ;
        
        // new User() ;
        
        log.info("p: {}", p) ;
        log.info("pics: {}", pics) ;

        Product product = new Product() ;
        product.setUser(user) ;
        product.setProductName(p.getProductName()) ;
        product.setProductPrice(p.getProductPrice()) ;
        product.setProductComment(p.getProductComment()) ;
        repository.save(product) ;
        log.info("Saved product with ID: {}", product.getProductPk()) ;
        // product.setProductLike(p.getProductLike()) ;


        List<String> picName = new ArrayList<>() ;
        if(pics != null){
            try{
                List<ProductPhoto> picsList = new ArrayList<>() ;
                // 파일 저장 경로 설정 및 폴더 생성
                String path = String.format("pic/%d", product.getProductPk()) ;
                log.info("path: {}", path);
                customFileUtils.makeFolders(path) ;

                for(MultipartFile pic : pics){
                    // 랜덤 파일 이름 생성 및 파일 저장
                    String saveFileName = customFileUtils.makeRandomFileName(pic) ;
                    log.info("saveFileName: {}", saveFileName) ;

                    String target = String.format("%s/%s", path, saveFileName) ;
                    log.info("Trying to load file from: {}", customFileUtils.uploadPath);
                    log.info("Saving file to: {}", target);

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
        }

        return 1 ;
    }

    // 상품 삭제
    @Transactional
    public int delProduct(String token, Long pk){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;

        Product product = repository.getReferenceById(pk) ;

        User user = userRepository.getReferenceById(userPk) ;
        if(product.getUser() != user){
            throw new RuntimeException("자신의 게시물이 아니면 삭제할 수 없습니다.") ;
        }

        // new Product() ;
        
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

    // 상품 수정
    @Transactional
    public int updateProduct(String token, UpdateProductReq p, List<MultipartFile> pics){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;


        // Optional로 Product 조회 및 예외 처리
        Product product = repository.getReferenceById(p.getProductPk()) ;

        if(product == null) {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }

        User user = userRepository.getReferenceById(userPk) ;
        if(product.getUser() != user){
            throw new RuntimeException("자신의 게시물이 아니면 수정할 수 없습니다.") ;
        }

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
            .map(product -> {
                List<ProductPhoto> productPhoto = photoRepository.findAllByProductPk(product.getProductPk());
                String pictureName;
                // 사진 리스트가 비어있는지 확인 후 처리
                if (productPhoto != null && !productPhoto.isEmpty()) {
                    pictureName = productPhoto.get(0).getProductPhoto(); // 적절한 메소드로 사진 이름 가져오기
                } else {
                    pictureName = "default.jpg"; // 사진이 없을 경우 기본값 설정
                }

                // GetProduct 객체 생성하면서 사진 URL 추가
                GetProduct getProduct = new GetProduct(product);
                getProduct.setPic("http://localhost:8080/pic/" + product.getProductPk() + "/" + pictureName);

                return getProduct;
            }) // Product를 GetProduct로 변환하는 로직
            .collect(Collectors.toList()) ;

        return productList ;
    }

    // 상품 상세조회
    public Product getSelectProduct(String token, long p){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;

        Product product = repository.getReferenceById(p) ;
        // new Product() ;
        return product ;
    }

    // 상품 사진 조회
    public List<ProductPhoto> getSelectPhoto(String token, long p){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;

        // Product 객체 가져오기
        Product product = repository.getReferenceById(p) ;
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

    // 상품 좋아요
    public int putProductLike(String token, long p) {
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userPk = userDetails.getMyUser().getUserPk() ;

        User user = userRepository.getReferenceById(userPk) ;
        Product product = repository.getReferenceById(p) ;

        ProductLike allLike = likeRepository.findProductLikeByUserPkAndProductPk(userPk, p) ;

        if(allLike == null ){
            ProductLike like = new ProductLike() ;
            like.setProduct(product) ;
            like.setUser(user) ;
            likeRepository.save(like) ;
            product.setProductLike(product.getProductLike() + 1) ;
            repository.save(product) ;
            return 1 ;
        } else {
            likeRepository.delete(allLike) ;
            product.setProductLike(product.getProductLike() - 1) ;
            repository.save(product) ;
            return 0 ;
        }

    }

    // 거래완료 컬럼 수정
    public int putProductTransaction(String token, long productPk){
        Authentication auth = jwtTokenProvider.getAuthentication(token) ;
        SecurityContextHolder.getContext().setAuthentication(auth) ;
        MyUserDetail userDetails = (MyUserDetail) auth.getPrincipal();
        long userId = userDetails.getMyUser().getUserPk() ;

        User user = userRepository.getReferenceById(userId) ;
        Product product = repository.getReferenceById(productPk) ;

        if(product.getUser() != user){
            throw new RuntimeException("본인 상품만 수정할 수 있습니다.") ;
        }

        product.setProductStatus(2) ;
        repository.save(product) ;

        return 1 ;
    }
}