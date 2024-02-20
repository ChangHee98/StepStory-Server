package org.donnguk.emodiary.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProvider {
    DEFAULT("DEFAULT"),
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    APPLE("APPLE");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
