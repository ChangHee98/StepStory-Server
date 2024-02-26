package com.kcs.stepstory.security.service;

import lombok.RequiredArgsConstructor;
import com.kcs.stepstory.dto.type.ErrorCode;
import com.kcs.stepstory.exception.CommonException;
import com.kcs.stepstory.repository.UserRepository;
import com.kcs.stepstory.security.info.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRepository.UserSecurityForm user = userRepository.findSecurityFormBySerialId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        UserRepository.UserSecurityForm user = userRepository.findSecurityFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LOGIN_USER));

        return UserPrincipal.create(user);
    }
}
