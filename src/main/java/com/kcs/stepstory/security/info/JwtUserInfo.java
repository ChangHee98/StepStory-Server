package dont.forget.springsecurity.security.info;

import dont.forget.springsecurity.dto.type.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}
