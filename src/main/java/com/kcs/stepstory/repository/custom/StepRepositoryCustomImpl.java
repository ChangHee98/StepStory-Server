package com.kcs.stepstory.repository.custom;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForBusanDto;
import com.kcs.stepstory.dto.response.StepCountForGyeonggiDto;
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
                case "Chungbuk":
                    builder.chungbuk(count);
                    break;
                case "Chungnam":
                    builder.chungnam(count);
                    break;
                case "Jeonbuk":
                    builder.jeonbuk(count);
                    break;
                case "Jeonnam":
                    builder.jeonnam(count);
                    break;
                case "Gyeongbuk":
                    builder.gyeongbuk(count);
                    break;
                case "Gyeongnam":
                    builder.gyeongnam(count);
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
                    break;
                case "Jongno":
                    builder.jongno(count);
                    break;
                case "Jung":
                    builder.jung(count);
                    break;
                case "Jungnang":
                    builder.jungnang(count);
                    break;
            }
        }
        return builder.build();
    }
    public StepCountForBusanDto countStepsForBusan() {
        List<Object[]> results = entityManager.createQuery(
                        "SELECT s.district, COUNT(s) FROM Step s WHERE s.province = 'Busan' GROUP BY s.district", Object[].class)
                .getResultList();

        StepCountForBusanDto.StepCountForBusanDtoBuilder builder = StepCountForBusanDto.builder();

        for(Object[] result : results){
            String district = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            switch (district){
                case "Haeundae":
                    builder.haeundae(count);
                    break;
                case "Gangseo":
                    builder.gangseo(count);
                    break;
                case "Geumjeong":
                    builder.geumjeong(count);
                    break;
                case "Gijang":
                    builder.gijang(count);
                    break;
                case "Busanjin":
                    builder.busanjin(count);
                    break;
                case "Dongrae":
                    builder.dongrae(count);
                    break;
                case "Dong":
                    builder.dong(count);
                    break;
                case "Nam":
                    builder.nam(count);
                    break;
                case "Saha":
                    builder.saha(count);
                    break;
                case "Sasang":
                    builder.sasang(count);
                    break;
                case "Seo":
                    builder.seo(count);
                    break;
                case "Suyeong":
                    builder.suyeong(count);
                    break;
                case "Yeongdo":
                    builder.yeongdo(count);
                    break;
            }
        }
        return builder.build();
    }
    public StepCountForGyeonggiDto countStepsForGyeonggi() {
        List<Object[]> results = entityManager.createQuery(
                        "SELECT s.district, COUNT(s) FROM Step s WHERE s.province = 'Gyeonggi' GROUP BY s.district", Object[].class)
                .getResultList();

        StepCountForGyeonggiDto.StepCountForGyeonggiDtoBuilder builder = StepCountForGyeonggiDto.builder();

        for(Object[] result : results) {
            String district = (String) result[0];
            Integer count = ((Number) result[1]).intValue();
            switch (district) {
                case "Goyang":
                    builder.goyang(count);
                    break;
                case "Paju":
                    builder.paju(count);
                    break;
                case "Guri":
                    builder.guri(count);
                    break;
                case "Gwangmyeong":
                    builder.gwangmyeong(count);
                    break;
                case "Gunpo":
                    builder.gunpo(count);
                    break;
                case "Gwacheon":
                    builder.gwacheon(count);
                    break;
                case "Gwangju":
                    builder.gwangju(count);
                    break;
                case "Gimpo":
                    builder.gimpo(count);
                    break;
                case "Namyangju":
                    builder.namyangju(count);
                    break;
                case "Dongducheon":
                    builder.dongducheon(count);
                    break;
                case "Bucheon":
                    builder.bucheon(count);
                    break;
                case "Seongnam":
                    builder.seongnam(count);
                    break;
                case "Siheung":
                    builder.siheung(count);
                    break;
                case "Ansan":
                    builder.ansan(count);
                    break;
                case "Anyang":
                    builder.anyang(count);
                    break;
                case "Yangju":
                    builder.yangju(count);
                    break;
                case "Yongin":
                    builder.yongin(count);
                    break;
                case "Yeoju":
                    builder.yeoju(count);
                    break;
                case "Icheon":
                    builder.icheon(count);
                    break;
                case "Uiwang":
                    builder.uiwang(count);
                    break;
                case "Hanam":
                    builder.hanam(count);
                    break;
                case "Hwaseong":
                    builder.hwaseong(count);
                    break;
                case "Anseong":
                    builder.anseong(count);
                    break;
                case "Osan":
                    builder.osan(count);
                    break;
                case "Pyeongtaek":
                    builder.pyeongtaek(count);
                    break;
                case "Pocheon":
                    builder.pocheon(count);
                    break;
                case "Gapyeong":
                    builder.gapyeong(count);
                    break;
                case "Yeoncheon":
                    builder.yeoncheon(count);
                    break;
                case "Yangpyeong":
                    builder.yangpyeong(count);
                    break;
                case "Suwon":
                    builder.suwon(count);
                    break;
                case "Uijeongbu":
                    builder.uijeongbu(count);
                    break;
            }
        }
        return builder.build();
    }
}
