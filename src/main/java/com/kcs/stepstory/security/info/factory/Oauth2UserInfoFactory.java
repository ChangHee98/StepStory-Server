package com.kcs.stepstory.security.info.factory;

import com.kcs.stepstory.dto.type.EProvider;
import com.kcs.stepstory.security.info.GoogleOauth2UserInfo;
import com.kcs.stepstory.security.info.KakaoOauth2UserInfo;

import java.util.Map;

public class Oauth2UserInfoFactory {
    public static Oauth2UserInfo getOauth2UserInfo(EProvider provider, Map<String, Object> attributes){
        switch (provider) {
            case KAKAO:
                return new KakaoOauth2UserInfo(attributes);
            case GOOGLE:
                return new GoogleOauth2UserInfo(attributes);
            default:
                throw new IllegalAccessError("잘못된 제공자 입니다.");
        }
    }
}
