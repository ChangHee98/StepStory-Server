package com.kcs.stepstory.repository.custom;

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



    StepCountForAllDto countMyStepsForAllRegions(String nickname);
    StepCountForSeoulDto countMyStepsForSeoul(String nickname);
    StepCountForBusanDto countMyStepsForBusan(String nickname);
    //    StepCountForDaeguDto countMyStepsForDaegu(String nickname);
//    StepCountForIncheonDto countMyStepsForIncheon(String nickname);
//    StepCountForGwangjuDto countMyStepsForGwangju(String nickname);
//    StepCountForDaejeonDto countMyStepsForDaejeon(String nickname);
//    StepCountForUlsanDto countMyStepsForUlsan(String nickname);
//    StepCountForSejongDto countMyStepsForSejong(String nickname);
    StepCountForGyeonggiDto countMyStepsForGyeonggi(String nickname);
//    StepCountForGangwonDto countMyStepsForGangwon(String nickname);
//    StepCountForChungbukDto countMyStepsForChungbuk(String nickname);
//    StepCountForChungnamDto countMyStepsForChungnam(String nickname);
//    StepCountForJeonbukDto countMyStepsForJeonbuk(String nickname);
//    StepCountForJeonnamDto countMyStepsForJeonnam(String nickname);
//    StepCountForGyeongbukDto countMyStepsForGyeongbuk(String nickname);
//    StepCountForGyeongnameDto countMyStepsForGyeongnam(String nickname);
//    StepCountForJejuDto countMyStepsForJeju(String nickname);
}
