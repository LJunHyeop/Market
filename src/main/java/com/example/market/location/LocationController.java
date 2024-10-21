package com.example.market.location;

import com.example.market.entity.Location;
import com.example.market.entity.User;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Tag(name = "지도 위치정보")
@RequestMapping("/api/administrative-areas")
public class LocationController {

    private final LocationService locationService;

    private final UserRepository userRepository;

    private final AuthenticationFacade authenticationFacade;

    private final LocationRepository locationRepository;

    private final String KAKAO_API_KEY = "a312e6959c793853678e53967eedcfa6";

    @PostMapping
    public ResponseEntity<Location> saveOrUpdateLocation(@RequestBody Location location) {
        Location savedLocation = locationService.saveOrUpdateLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.OK);
    }

    // 특정 사용자에 대한 행정구역 조회
    @GetMapping("/user")
    public ResponseEntity<Location> getLocationByUser() {
        // 로그인한 사용자 정보 가져오기
        User user = getCurrentUser(authenticationFacade.getLoginUser());

        // 사용자의 위치를 조회
        Location location = locationService.getLocationByUser(user);

        if (location != null) {
            return new ResponseEntity<>(location, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }
}