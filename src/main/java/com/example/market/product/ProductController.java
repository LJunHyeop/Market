package com.example.market.product ;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.market.product.model.PostProductRegistrationReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
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
    public ResponseEntity postProduct(HttpServletRequest req, @RequestBody PostProductRegistrationReq p){
        // String token = tokenProvider.resolveToken(req) ;
        String token = null ;
        int result = service.postProduct(token, p) ;
        return ResponseEntity.ok().body(result) ;
    }
}