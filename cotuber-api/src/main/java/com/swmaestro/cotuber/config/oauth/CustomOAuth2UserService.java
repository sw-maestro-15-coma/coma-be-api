package com.swmaestro.cotuber.config.oauth;

import com.swmaestro.cotuber.user.OAuthUserInfo;
import com.swmaestro.cotuber.user.User;
import com.swmaestro.cotuber.user.UserEditor;
import com.swmaestro.cotuber.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserReader userReader;
    private final UserEditor userEditor;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> userAttribute = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuthUserInfo userInfo = createOAuthUserInfo(userAttribute, registrationId);

        User user = getOrSave(userInfo);
        return new PrincipalDetail(user, userAttribute);
    }

    private OAuthUserInfo createOAuthUserInfo(Map<String, Object> userInfo, String registrationId) {
        return OAuthUserInfo.builder()
                .oAuthId(registrationId + "_" + userInfo.get("sub").toString())
                .email(userInfo.get("email").toString())
                .nickname(userInfo.get("name").toString())
                .profileImageUrl(userInfo.get("picture").toString())
                .build();
    }

    private User getOrSave(OAuthUserInfo oAuthUserInfo) {
        return userReader.findByOAuthId(oAuthUserInfo.oAuthId())
                .orElseGet(() -> {
                    User user = User.of(oAuthUserInfo);
                    return userEditor.save(user);
                });
    }
}
