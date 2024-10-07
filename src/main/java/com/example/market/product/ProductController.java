package com.example.market.product ;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.market.product.model.PostProductRegistrationReq;
import com.example.market.product.model.UpdateProductReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/product")
@Tag(name="상품", description="상품 CRUD")
public class ProductController {
    private final ProductService service ;

    // 상품등록
    @PostMapping("/registration")
    @Operation(summary = "상품등록")
    public ResponseEntity postProduct(HttpServletRequest req, @RequestPart PostProductRegistrationReq p, @RequestPart List<MultipartFile> pics){
        // String token = tokenProvider.resolveToken(req) ;
        String token = null ;
        int result = service.postProduct(token, p, pics) ;
        return ResponseEntity.ok().body(result) ;
    }

    // 상품 삭제
    @DeleteMapping("/delete")
    @Operation(summary = "상품삭제")
    public ResponseEntity delProduct(HttpServletRequest req, @ModelAttribute @ParameterObject Long productPk){
        String token = null ;
        int result = service.delProduct(token, productPk) ;


        return ResponseEntity.ok().body(result) ;
    }

    // 상품 수정
    @PutMapping("/update")
    @Operation(summary = "상품 수정")
    public ResponseEntity updateProduct(HttpServletRequest req, @RequestPart UpdateProductReq p, @RequestPart List<MultipartFile> pics){
        String token = null ;
        int result = service.updateProduct(token, p, pics) ;

        return ResponseEntity.ok().body(result) ;
    }
}