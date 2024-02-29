package com.kcs.stepstory.repository;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
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
}
