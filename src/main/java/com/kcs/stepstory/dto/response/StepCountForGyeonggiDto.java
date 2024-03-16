package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;

@Builder
public record StepCountForGyeonggiDto(
        Integer suwon,
        Integer seongnam,
        Integer yongin,
        Integer anyang,
        Integer ansan,
        Integer gwacheon,
        Integer gwangmyeong,
        Integer gwangju,
        Integer gunpo,
        Integer bucheon,
        Integer siheung,
        Integer gimpo,
        Integer anseong,
        Integer osan,
        Integer uiwang,
        Integer icheon,
        Integer pyeongtaek,
        Integer hanam,
        Integer hwaseong,
        Integer yeoju,
        Integer yangpyeong,
        Integer goyang,
        Integer guri,
        Integer namyangju,
        Integer dongducheon,
        Integer yangju,
        Integer uijeongbu,
        Integer paju,
        Integer pocheon,
        Integer yeoncheon,
        Integer gapyeong
) implements StepCountDto{
    //StepCountForSeoulDtoBuilder
    public static StepCountForGyeonggiDto fromGyeonggiArea(Step step) {
        StepCountForGyeonggiDto.StepCountForGyeonggiDtoBuilder builder = StepCountForGyeonggiDto.builder()
                .suwon(0)
                .seongnam(0)
                .yongin(0)
                .anyang(0)
                .ansan(0)
                .gwacheon(0)
                .gwangmyeong(0)
                .gwangju(0)
                .gunpo(0)
                .bucheon(0)
                .siheung(0)
                .gimpo(0)
                .anseong(0)
                .osan(0)
                .uiwang(0)
                .icheon(0)
                .pyeongtaek(0)
                .hanam(0)
                .hwaseong(0)
                .yeoju(0)
                .yangpyeong(0)
                .goyang(0)
                .guri(0)
                .namyangju(0)
                .dongducheon(0)
                .yangju(0)
                .uijeongbu(0)
                .paju(0)
                .pocheon(0)
                .yeoncheon(0)
                .gapyeong(0);

        String area = step.getCity();
        switch (area) {
            case "Suwon":
                builder.suwon(1);
                break;
            case "Seongnam":
                builder.seongnam(1);
                break;
            case "Yongin":
                builder.yongin(1);
                break;
            case "Anyang":
                builder.anyang(1);
                break;
            case "Ansan":
                builder.ansan(1);
                break;
            case "Gwacheon":
                builder.gwacheon(1);
                break;
            case "Gwangmyeong":
                builder.gwangmyeong(1);
                break;
            case "Gwangju":
                builder.gwangju(1);
                break;
            case "Gunpo":
                builder.gunpo(1);
                break;
            case "Bucheon":
                builder.bucheon(1);
                break;
            case "Siheung":
                builder.siheung(1);
                break;
            case "Gimpo":
                builder.gimpo(1);
                break;
            case "Anseong":
                builder.anseong(1);
                break;
            case "Osan":
                builder.osan(1);
                break;
            case "Uiwang":
                builder.uiwang(1);
                break;
            case "Icheon":
                builder.icheon(1);
                break;
            case "Pyeongtaek":
                builder.pyeongtaek(1);
                break;
            case "Hanam":
                builder.hanam(1);
                break;
            case "Hwaseong":
                builder.hwaseong(1);
                break;
            case "Yeoju":
                builder.yeoju(1);
                break;
            case "Yangpyeong":
                builder.yangpyeong(1);
                break;
            case "Goyang":
                builder.goyang(1);
                break;
            case "Guri":
                builder.guri(1);
                break;
            case "Namyangju":
                builder.namyangju(1);
                break;
            case "Dongducheon":
                builder.dongducheon(1);
                break;
            case "Yangju":
                builder.yangju(1);
                break;
            case "Uijeongbu":
                builder.uijeongbu(1);
                break;
            case "Paju":
                builder.paju(1);
                break;
            case "Pocheon":
                builder.pocheon(1);
                break;
            case "Yeoncheon":
                builder.yeoncheon(1);
                break;
            case "Gapyeong":
                builder.gapyeong(1);
                break;
            default:
                break;
        }
        return builder.build();
    }

}

