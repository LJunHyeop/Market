package com.example.market.manner;

import com.example.market.manner.common.MannerRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manner")
@Tag(name="매너", description="매너 평가 항목")
public class MannerController {
    private final MannerService service;

//    @PostMapping
    @GetMapping("/assessment")
    @Operation(summary = "거래 후 평가 페이지")
    public List<MannerRes> readManner(){
       return service.readManner();
    }
//    @PutMapping
//    @DeleteMapping
}
