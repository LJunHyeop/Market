package com.example.market.location;

import com.example.market.entity.Location;
import com.example.market.entity.User;
import com.example.market.security.AuthenticationFacade;
import com.example.market.security.MyUser;
import com.example.market.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final AuthenticationFacade authenticationFacade;


    public Location saveOrUpdateLocation(Location location) {
        // 현재 로그인한 사용자 정보 조회
        User user = getCurrentUser(authenticationFacade.getLoginUser());

        // 기존 위치를 조회
        Location existingLocation = locationRepository.findByUserPk(user);
        if (existingLocation != null) {
            // 기존 위치가 있다면 업데이트
            existingLocation.setLatitude(location.getLatitude());
            existingLocation.setLongitude(location.getLongitude());
            existingLocation.setAdministrativeDistrict(location.getAdministrativeDistrict());
            existingLocation.setUserPk(user); // userPk 설정
            return locationRepository.save(existingLocation);
        } else {
            // 기존 위치가 없다면 새로 저장
            location.setUserPk(user); // 새로운 위치 저장 시 userPk 설정
            return locationRepository.save(location);
        }
    }


    // 특정 사용자에 대한 행정구역 조회
    private User getCurrentUser(MyUser myUser) {
        return userRepository.findById(myUser.getUserPk()).orElse(null);
    }
    // 추가적인 서비스 메소드가 필요하면 여기에 작성
    public Location getLocationByUser(User user) {
        return locationRepository.findByUserPk(user);
    }
}