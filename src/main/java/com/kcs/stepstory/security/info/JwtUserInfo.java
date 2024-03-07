package com.kcs.stepstory.security.info;

import com.kcs.stepstory.dto.type.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}
