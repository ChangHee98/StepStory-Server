package com.kcs.stepstory.service;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.dto.response.FriendListDto;
import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;
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


    /**
     * 발자국 조회 - 전체 서비스 로직
     */
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

        // for문 : fromAllArea() 이용하여 Dto변환,  allStepAaccumulate() 이용하여 누적 count
        for (Step step : stepList) {
            StepCountForAllDto stepDto = StepCountForAllDto.fromAllArea(step);
            totalStepCount = allStepAaccumulate(totalStepCount, stepDto);
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


    /**
     * 발자국 조회 - 전체 누적 count 메서드
     */
    private StepCountForAllDto allStepAaccumulate(StepCountForAllDto totalStepCount, StepCountForAllDto stepCount) {
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


    /**
     * 발자국 조회 - 서울  서비스 로직
     */
    public StepCountForSeoulDto getMyStepCountForSeoul(Long userId, String provice) {
        List<Step> stepList = myStepRepository.findBySeoulListForMyStep(userId, provice);
        StepCountForSeoulDto totalStepCount = StepCountForSeoulDto.builder()
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
                .jungnang(0)
                .build();

        for(Step step : stepList) {
            StepCountForSeoulDto stepDto = StepCountForSeoulDto.fromSeoulArea(step);
            totalStepCount = seoulStepAaccumulate(totalStepCount, stepDto);
        }


        return totalStepCount;
    }

    /**
     * 발자국 조회 - 서울  서비스 로직
     */
    private StepCountForSeoulDto  seoulStepAaccumulate(StepCountForSeoulDto totalStepCount, StepCountForSeoulDto stepCount) {
        return StepCountForSeoulDto.builder()
                .gangnam(totalStepCount.gangnam() + stepCount.gangnam())
                .gangdong(totalStepCount.gangdong() + stepCount.gangdong())
                .gangbuk(totalStepCount.gangbuk() + stepCount.gangbuk())
                .gangseo(totalStepCount.gangseo() + stepCount.gangseo())
                .gwanak(totalStepCount.gwanak() + stepCount.gwanak())
                .gwangjin(totalStepCount.gwangjin() + stepCount.gwangjin())
                .guro(totalStepCount.guro() + stepCount.guro())
                .geumcheon(totalStepCount.geumcheon() + stepCount.geumcheon())
                .nowon(totalStepCount.nowon() + stepCount.nowon())
                .dobong(totalStepCount.dobong() + stepCount.dobong())
                .dongdaemun(totalStepCount.dongdaemun() + stepCount.dongdaemun())
                .dongjak(totalStepCount.dongjak() + stepCount.dongjak())
                .mapo(totalStepCount.mapo() + stepCount.mapo())
                .seodaemun(totalStepCount.seodaemun() + stepCount.seodaemun())
                .seocho(totalStepCount.seocho() + stepCount.seocho())
                .seongdong(totalStepCount.seongdong() + stepCount.seongdong())
                .seongbuk(totalStepCount.seongbuk() + stepCount.seongbuk())
                .songpa(totalStepCount.songpa() + stepCount.songpa())
                .yangcheon(totalStepCount.yangcheon() + stepCount.yangcheon())
                .yeongdeungpo(totalStepCount.yeongdeungpo() + stepCount.yeongdeungpo())
                .yongsan(totalStepCount.yongsan() + stepCount.yongsan())
                .eunpyeong(totalStepCount.eunpyeong() + stepCount.eunpyeong())
                .jongno(totalStepCount.jongno() + stepCount.jongno())
                .jung(totalStepCount.jung() + stepCount.jung())
                .jungnang(totalStepCount.jungnang() + stepCount.jungnang())
                .build();
    }



}