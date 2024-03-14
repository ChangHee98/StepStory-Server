package com.kcs.stepstory.service;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.dto.response.FriendListDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.repository.MyStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyStepService {

    private final MyStepRepository myStepRepository;

    public StepCountForAllDto getMyStepCountForAll(Long userId) {
        List<Step> stepList = myStepRepository.findByAllAreaListForMyStep(userId);
        // StepCountForAllDto 자체가 여러 필드로 구성되어 있어, List로 담아줄 필요 없다.
        StepCountForAllDto totalStepCount = StepCountForAllDto.builder()
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
                .jeju(0)
                .build();


        for (Step step : stepList) {
            StepCountForAllDto stepDto = StepCountForAllDto.fromAllArea(step);
            totalStepCount = accumulate(totalStepCount, stepDto);
            log.info("totalStepCount : "+ totalStepCount);
        }

        return StepCountForAllDto.builder()
                .seoul(totalStepCount.seoul())
                .busan(totalStepCount.busan())
                .incheon(totalStepCount.incheon())
                .gwanju(totalStepCount.gwanju())
                .ulsan(totalStepCount.ulsan())
                .sejong(totalStepCount.sejong())
                .gyeonggi(totalStepCount.gyeonggi())
                .gangwon(totalStepCount.gangwon())
                .chungbuk(totalStepCount.chungbuk())
                .chungnam(totalStepCount.chungnam())
                .jeonbuk(totalStepCount.jeonbuk())
                .jeonnam(totalStepCount.jeonnam())
                .gyeongbuk(totalStepCount.gyeongbuk())
                .gyeongnam(totalStepCount.gyeongnam())
                .jeju(totalStepCount.jeju())
                .build();

    }

    private StepCountForAllDto accumulate(StepCountForAllDto totalStepCount, StepCountForAllDto stepCount) {
        log.info("stepCount.seoul : " + stepCount.seoul());
        return StepCountForAllDto.builder()
                .seoul(totalStepCount.seoul() + stepCount.seoul())
                .busan(totalStepCount.busan() + stepCount.busan())
                .incheon(totalStepCount.incheon() + stepCount.incheon())
                .gwanju(totalStepCount.gwanju() + stepCount.gwanju())
                .ulsan(totalStepCount.ulsan() + stepCount.ulsan())
                .sejong(totalStepCount.sejong() + stepCount.sejong())
                .gyeonggi(totalStepCount.gyeonggi() + stepCount.gyeonggi())
                .gangwon(totalStepCount.gangwon() + stepCount.gangwon())
                .chungbuk(totalStepCount.chungbuk() + stepCount.chungbuk())
                .chungnam(totalStepCount.chungnam() + stepCount.chungnam())
                .jeonbuk(totalStepCount.jeonbuk() + stepCount.jeonbuk())
                .jeonnam(totalStepCount.jeonnam() + stepCount.jeonnam())
                .gyeongbuk(totalStepCount.gyeongbuk() + stepCount.gyeongbuk())
                .gyeongnam(totalStepCount.gyeongnam() + stepCount.gyeongnam())
                .jeju(totalStepCount.jeju() + stepCount.jeju())
                .build();
    }
}
