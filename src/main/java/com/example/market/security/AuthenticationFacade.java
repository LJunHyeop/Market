package com.example.market.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public MyUser getLoginUser() {
        try {
            MyUserDetail myUserDetails = (MyUserDetail) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return myUserDetails == null ? null : myUserDetails.getMyUser();
        } catch (Exception e) {
            return null;
        }
    }

    public long getLoginUserId() {
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserPk();
    }

}