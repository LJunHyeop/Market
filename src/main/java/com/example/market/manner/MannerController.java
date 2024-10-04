package com.example.market.manner;

import com.example.market.manner.common.MannerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("manner")
public class MannerController {
    private final MannerService service;

//    @PostMapping
    @GetMapping("assessment")
    public List<MannerRes> readManner(){
       return service.readManner();
    }
//    @PutMapping
//    @DeleteMapping
}
