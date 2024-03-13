package com.kcs.stepstory.dto.response;

import com.kcs.stepstory.domain.Step;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public record StepCountForAllDto (
        Integer seoul,
        Integer busan,
        Integer incheon,
        Integer gwanju,
        Integer ulsan,
        Integer sejong,
        Integer gyeonggi,
        Integer gangwon,
        Integer chungbuk,
        Integer chungnam,
        Integer jeonbuk,
        Integer jeonnam,
        Integer gyeongbuk,
        Integer gyeongnam,
        Integer jeju
) {

    public static StepCountForAllDto fromAllArea(Step step) {
        StepCountForAllDtoBuilder builder = StepCountForAllDto.builder()
                .seoul(0)
                .busan(0)
                .incheon(0)
                .gwanju(0)
                .ulsan(0)
                .sejong(0)
                .gyeonggi(0)
                .gangwon(0)
                .chungbuk(0)
                .chungnam(0)
                .jeonbuk(0)
                .jeonnam(0)
                .gyeongbuk(0)
                .gyeongnam(0)
                .jeju(0);

        String area = step.getProvince();
        switch (area) {
            case "Seoul":
                builder.seoul(1);
                break;
            case "Busan":
                builder.busan(1);
                break;
            case "Incheon":
                builder.incheon(1);
                break;
            case "Gwangju":
                builder.gwanju(1);
                break;
            case "Ulsan":
                builder.ulsan(1);
                break;
            case "Sejong":
                builder.sejong(1);
                break;
            case "Gyeonggi":
                builder.gyeonggi(1);
                break;
            case "Gangwon":
                builder.gangwon(1);
                break;
            case "Chungbuk":
                builder.chungbuk(1);
                break;
            case "Chungnam":
                builder.chungnam(1);
                break;
            case "Jeonbuk":
                builder.jeonbuk(1);
                break;
            case "Jeonnam":
                builder.jeonnam(1);
                break;
            case "Gyeongbuk":
                builder.gyeongbuk(1);
                break;
            case "Gyeongnam":
                builder.gyeongnam(1);
                break;
            case "Jeju":
                builder.jeju(1);
                break;
            default:
                break;
        }
        log.info(builder.toString());
        return builder.build();
    }

}
