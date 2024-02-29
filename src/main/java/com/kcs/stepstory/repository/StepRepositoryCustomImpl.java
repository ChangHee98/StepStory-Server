package com.kcs.stepstory.repository;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class StepRepositoryCustomImpl implements StepRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StepCountForAllDto countStepsForAllRegions() {
        List<Object[]> results = entityManager.createQuery(
                        "SELECT s.province, COUNT(s) FROM Step s GROUP BY s.province", Object[].class)
                .getResultList();

        StepCountForAllDto.StepCountForAllDtoBuilder builder = StepCountForAllDto.builder();

        // 결과 처리
        for (Object[] result : results) {
            String province = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            switch (province) {
                case "Seoul":
                    builder.seoul(count);
                    break;
                case "Busan":
                    builder.busan(count);
                    break;
                case "Incheon":
                    builder.incheon(count);
                    break;
                case "Gwangju":
                    builder.gwanju(count);
                    break;
                case "Ulsan":
                    builder.ulsan(count);
                    break;
                case "Sejong":
                    builder.sejong(count);
                    break;
                case "Gyeonggi":
                    builder.gyeonggi(count);
                    break;
                case "Gangwon":
                    builder.gangwon(count);
                    break;
                case "North Chungcheong":
                    builder.northChungcheong(count);
                    break;
                case "South Chungcheong":
                    builder.southChungcheong(count);
                    break;
                case "North Jeolla":
                    builder.northJeolla(count);
                    break;
                case "South Jeolla":
                    builder.southJeolla(count);
                    break;
                case "North Gyeongsang":
                    builder.northGyeongsang(count);
                    break;
                case "South Gyeongsang":
                    builder.southGyeongsang(count);
                    break;
                case "Jeju":
                    builder.jeju(count);
                    break;
            }
        }
        return builder.build();
    }
    @Override
    public StepCountForSeoulDto countStepsForSeoul() {
        List<Object[]> results = entityManager.createQuery(
                        "SELECT s.district, COUNT(s) FROM Step s WHERE s.province = 'Seoul' GROUP BY s.district", Object[].class)
                .getResultList();

        StepCountForSeoulDto.StepCountForSeoulDtoBuilder builder = StepCountForSeoulDto.builder();

        // 결과 처리
        for (Object[] result : results) {
            String district = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            switch (district) {
                case "Gangnam":
                    builder.gangnam(count);
                    break;
                case "Gangdong":
                    builder.gangdong(count);
                    break;
                case "Gangbuk":
                    builder.gangbuk(count);
                    break;
                case "Gangseo":
                    builder.gangseo(count);
                    break;
                case "Gwanak":
                    builder.gwanak(count);
                    break;
                case "Gwangjin":
                    builder.gwangjin(count);
                    break;
                case "Guro":
                    builder.guro(count);
                    break;
                case "Geumcheon":
                    builder.geumcheon(count);
                    break;
                case "Nowon":
                    builder.nowon(count);
                    break;
                case "Dobong":
                    builder.dobong(count);
                    break;
                case "Dongdaemun":
                    builder.dongdaemun(count);
                    break;
                case "Dongjak":
                    builder.dongjak(count);
                    break;
                case "Mapo":
                    builder.mapo(count);
                    break;
                case "Seodaemun":
                    builder.seodaemun(count);
                    break;
                case "Seocho":
                    builder.seocho(count);
                    break;
                case "Seongdong":
                    builder.seongdong(count);
                    break;
                case "Seongbuk":
                    builder.seongbuk(count);
                    break;
                case "Songpa":
                    builder.songpa(count);
                    break;
                case "Yangcheon":
                    builder.yangcheon(count);
                    break;
                case "Yeongdeungpo":
                    builder.yeongdeungpo(count);
                    break;
                case "Yongsan":
                    builder.yongsan(count);
                    break;
                case "Eunpyeong":
                    builder.eunpyeong(count);
            }
        }
        return builder.build();
    }
}
