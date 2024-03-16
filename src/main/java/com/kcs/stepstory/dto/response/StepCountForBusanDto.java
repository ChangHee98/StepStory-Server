package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;

@Builder
public record StepCountForBusanDto(

        Integer jung,
        Integer dong,
        Integer seo,
        Integer nam,
        Integer buk,
        Integer yeongdo,
        Integer busanjin,
        Integer dongrae,
        Integer haeundae,
        Integer saha,
        Integer geumjeong,
        Integer gangseo,
        Integer yeonje,
        Integer suyeong,
        Integer sasang,
        Integer gijang
)implements StepCountDto {


    public static StepCountForBusanDto fromBusanArea(Step step) {
        StepCountForBusanDto.StepCountForBusanDtoBuilder builder = StepCountForBusanDto.builder()
                .jung(0)
                .dong(0)
                .seo(0)
                .nam(0)
                .buk(0)
                .yeongdo(0)
                .busanjin(0)
                .dongrae(0)
                .haeundae(0)
                .saha(0)
                .geumjeong(0)
                .gangseo(0)
                .yeonje(0)
                .suyeong(0)
                .sasang(0)
                .gijang(0);

        String area = step.getDistrict();
        switch (area) {
            case "Jung":
                builder.jung(1);
                break;
            case "Dong":
                builder.dong(1);
                break;
            case "Seo":
                builder.seo(1);
                break;
            case "Nam":
                builder.nam(1);
                break;
            case "Buk":
                builder.buk(1);
                break;
            case "Yeongdo":
                builder.yeongdo(1);
                break;
            case "Busanjin":
                builder.busanjin(1);
                break;
            case "Dongrae":
                builder.dongrae(1);
                break;
            case "Haeundae":
                builder.haeundae(1);
                break;
            case "Saha":
                builder.saha(1);
                break;
            case "Geumjeong":
                builder.geumjeong(1);
                break;
            case "Gangseo":
                builder.gangseo(1);
                break;
            case "Yeonje":
                builder.yeonje(1);
                break;
            case "Suyeong":
                builder.suyeong(1);
                break;
            case "Sasang":
                builder.sasang(1);
                break;
            case "Gijang":
                builder.gijang(1);
                break;
        }
        return builder.build();
    }
}


