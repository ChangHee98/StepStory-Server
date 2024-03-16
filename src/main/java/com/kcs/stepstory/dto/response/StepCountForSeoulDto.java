package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;

@Builder
public record StepCountForSeoulDto(
        Integer gangnam,
        Integer gangdong,
        Integer gangbuk,
        Integer gangseo,
        Integer gwanak,
        Integer gwangjin,
        Integer guro,
        Integer geumcheon,
        Integer nowon,
        Integer dobong,
        Integer dongdaemun,
        Integer dongjak,
        Integer mapo,
        Integer seodaemun,
        Integer seocho,
        Integer seongdong,
        Integer seongbuk,
        Integer songpa,
        Integer yangcheon,
        Integer yeongdeungpo,
        Integer yongsan,
        Integer eunpyeong,
        Integer jongno,
        Integer jung,
        Integer jungnang
) implements StepCountDto{


    /**
     * 서울 조회 fromEntity 메서드
     */
    public static StepCountForSeoulDto fromSeoulArea(Step step) {
        StepCountForSeoulDto.StepCountForSeoulDtoBuilder builder = StepCountForSeoulDto.builder()
                .gangnam(0)
                .gangdong(0)
                .gangbuk(0)
                .gangseo(0)
                .gwanak(0)
                .gwangjin(0)
                .guro(0)
                .geumcheon(0)
                .nowon(0)
                .dobong(0)
                .dongdaemun(0)
                .dongjak(0)
                .mapo(0)
                .seodaemun(0)
                .seocho(0)
                .seongdong(0)
                .seongbuk(0)
                .songpa(0)
                .yangcheon(0)
                .yeongdeungpo(0)
                .yongsan(0)
                .eunpyeong(0)
                .jongno(0)
                .jung(0)
                .jungnang(0);


        String area = step.getDistrict();
        switch (area) {
            case "gangnam":
                builder.gangnam(1);
                break;
            case "gangdong":
                builder.gangdong(1);
                break;
            case "gangbuk":
                builder.gangbuk(1);
                break;
            case "gangseo":
                builder.gangseo(1);
                break;
            case "gwanak":
                builder.gwanak(1);
                break;
            case "gwangjin":
                builder.gwangjin(1);
                break;
            case "guro":
                builder.guro(1);
                break;
            case "geumcheon":
                builder.geumcheon(1);
                break;
            case "nowon":
                builder.nowon(1);
                break;
            case "dobong":
                builder.dobong(1);
                break;
            case "dongdaemun":
                builder.dongdaemun(1);
                break;
            case "dongjak":
                builder.dongjak(1);
                break;
            case "mapo":
                builder.mapo(1);
                break;
            case "seodaemun":
                builder.seodaemun(1);
                break;
            case "seocho":
                builder.seocho(1);
                break;
            case "seongdong":
                builder.seongdong(1);
                break;
            case "seongbuk":
                builder.seongbuk(1);
                break;
            case "songpa":
                builder.songpa(1);
                break;
            case "yangcheon":
                builder.yangcheon(1);
                break;
            case "yeongdeungpo":
                builder.yeongdeungpo(1);
                break;
            case "yongsan":
                builder.yongsan(1);
                break;
            case "eunpyeong":
                builder.eunpyeong(1);
                break;
            case "jongno":
                builder.jongno(1);
                break;
            case "jung":
                builder.jung(1);
                break;
            case "jungnang":
                builder.jungnang(1);
                break;
            default:
                break;
        }

        return builder.build();
    }

}

