package com.example.market.product ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.market.jwt.JwtTokenProvider;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.market.entity.Product;
import com.example.market.entity.ProductPhoto;
import com.example.market.product.model.GetProduct;
import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.model.UpdateProductReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/product")
@Tag(name="상품", description="상품 CRUD")
public class ProductController {
    private final ProductService service ;
    private final JwtTokenProvider tokenService ;

    // 상품등록
    @PostMapping("/registration")
    @Operation(summary = "상품등록")
    public ResponseEntity postProduct(HttpServletRequest req, @RequestPart PostProductRegistrationReq p, @RequestPart List<MultipartFile> pics){
        String token = tokenService.resolveToken(req) ;
        int result = service.postProduct(token, p, pics) ;
        return ResponseEntity.ok().body(result) ;
    }

    // 상품 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "상품삭제")
    public ResponseEntity delProduct(HttpServletRequest req, @ModelAttribute @ParameterObject Long productPk){
        String token = tokenService.resolveToken(req) ;
        int result = service.delProduct(token, productPk) ;


        return ResponseEntity.ok().body(result) ;
    }

    // 상품 수정
    @PutMapping("/update")
    @Operation(summary = "상품 수정")
    public ResponseEntity updateProduct(HttpServletRequest req, @RequestPart UpdateProductReq p, @RequestPart List<MultipartFile> pics){
        String token = tokenService.resolveToken(req) ;
        int result = service.updateProduct(token, p, pics) ;

        return ResponseEntity.ok().body(result) ;
    }

    // 전체 검색, 검색어 검색
    @GetMapping("/get")
    @Operation(summary = "상품 검색", description = "검색어 사용")
    public ResponseEntity getAllProduct
    (
        HttpServletRequest req, 
        @RequestParam(required = false) String p,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size
    ) 
    {
        String token = tokenService.resolveToken(req) ;
        List<GetProduct> list = service.getAllProduct(token, p, page, size) ;


        // 전체 항목 수를 기반으로 총 페이지 수 계산
        long totalCount = service.getTotalCount(p) ;
        int totalPages = (int) Math.ceil((double) totalCount / size) ;

                // 현재 페이지 조건에 따라 "더 보기" 및 "앞으로 가기" 조건 설정
        boolean showMore = totalPages > (page + 1) && (page + 1) % 10 == 0 ;
        boolean showBack = page >= 10 ;

        // 페이징 정보 포함하여 응답 구성
        Map<String, Object> response = new HashMap<>() ;
        response.put("products", list) ;
        response.put("currentPage", page) ;
        response.put("totalPages", totalPages) ;
        response.put("hasMorePages", totalPages > 10) ;
        response.put("showMore", showMore) ;
        response.put("showBack", showBack) ;

        return ResponseEntity.ok().body(response) ;
    }
    
    @GetMapping("/selectGet")
    @Operation(summary = "상세조회", description = "p = 상품 PK값")
    public ResponseEntity getSelectProduct(HttpServletRequest req, @ModelAttribute @ParameterObject long p){
        String token = tokenService.resolveToken(req) ;
        
        Product product = service.getSelectProduct(token, p) ;
        List<ProductPhoto> productPhoto = service.getSelectPhoto(token, p) ;

        Map<String, Object> response = new HashMap<>() ;
        response.put("product", product) ;
        response.put("productPhoto", productPhoto) ;

        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }

    @PutMapping("/updateLike")
    @Operation(summary = "상품 좋아요", description = "p = 상품 PK값")
    public ResponseEntity putMethodName(HttpServletRequest req, @ModelAttribute @ParameterObject long p) {
        String token = tokenService.resolveToken(req) ;
        int result = service.putProductLike(token, p) ;
        
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    @PutMapping("/TransactionCompleted")
    @Operation(summary = "거래 완료", description = "1: 거래중, 2: 거래완료, 3: 평가완료")
    public ResponseEntity transactionCompleted
    (
        HttpServletRequest req,
        @ModelAttribute @ParameterObject long productPk
    )
    {
        String token = tokenService.resolveToken(req) ;
        int result = service.putProductTransaction(token, productPk) ;

        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }
}