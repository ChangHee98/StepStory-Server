package com.kcs.stepstory.repository;

import com.kcs.stepstory.dto.response.StepCountForAllDto;
import com.kcs.stepstory.dto.response.StepCountForBusanDto;
import com.kcs.stepstory.dto.response.StepCountForGyeonggiDto;
import com.kcs.stepstory.dto.response.StepCountForSeoulDto;

public interface StepRepositoryCustom {
    StepCountForAllDto countStepsForAllRegions();
    StepCountForSeoulDto countStepsForSeoul();
    StepCountForBusanDto countStepsForBusan();
//    StepCountForDaeguDto countStepsForDaegu();
//    StepCountForIncheonDto countStepsForIncheon();
//    StepCountForGwangjuDto countStepsForGwangju();
//    StepCountForDaejeonDto countStepsForDaejeon();
//    StepCountForUlsanDto countStepsForUlsan();
//    StepCountForSejongDto countStepsForSejong();
    StepCountForGyeonggiDto countStepsForGyeonggi();
//    StepCountForGangwonDto countStepsForGangwon();
//    StepCountForChungbukDto countStepsForChungbuk();
//    StepCountForChungnamDto countStepsForChungnam();
//    StepCountForJeonbukDto countStepsForJeonbuk();
//    StepCountForJeonnamDto countStepsForJeonnam();
//    StepCountForGyeongbukDto countStepsForGyeongbuk();
//    StepCountForGyeongnameDto countStepsForGyeongnam();
//    StepCountForJejuDto countStepsForJeju();
}
