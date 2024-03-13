package com.kcs.stepstory.service;

import com.kcs.stepstory.dto.response.*;
import com.kcs.stepstory.repository.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepService {
    private final StepRepository stepRepository;

    public StepCountForAllDto getStepCountForAll(){
        return stepRepository.countStepsForAllRegions();
    }
    public StepCountForSeoulDto getStepCountForSeoul(){
        return stepRepository.countStepsForSeoul();
    }
    public StepCountForBusanDto getStepCountForBusan(){
        return stepRepository.countStepsForBusan();
    }
    /* public StepCountForSeoulDto getStepCountForDaegu(){
        return stepRepository.countStepsForDaegu();
    }
    public StepCountForSeoulDto getStepCountForIncheon(){
        return stepRepository.countStepsForIncheon();
    }
    public StepCountForSeoulDto getStepCountForGwangju(){
        return stepRepository.countStepsForGwangju();
    }
    public StepCountForSeoulDto getStepCountForDaejeon(){
        return stepRepository.countStepsForDaejeon();
    }
    public StepCountForSeoulDto getStepCountForUlsan(){
        return stepRepository.countStepsForUlsan();
    }
    public StepCountForSeoulDto getStepCountForSejong(){
        return stepRepository.countStepsForSejong();
    }    */
    public StepCountDto getStepCountForGyeonggi(){
        return stepRepository.countStepsForGyeonggi();
    }
    /* public StepCountForSeoulDto getStepCountForGangwon(){
        return stepRepository.countStepsForGangwon();
    }
    public StepCountForSeoulDto getStepCountForChungbuk(){
        return stepRepository.countStepsForChungbuk();
    }
    public StepCountForSeoulDto getStepCountForChungnam(){
        return stepRepository.countStepsForChungnam();
    }
    public StepCountForSeoulDto getStepCountForJeonbuk(){
        return stepRepository.countStepsForJeonbuk();
    }
    public StepCountForSeoulDto getStepCountForJeonnam(){
        return stepRepository.countStepsForJeonnam();
    }
    public StepCountForSeoulDto getStepCountForGyeongbuk(){
        return stepRepository.countStepsForGyeongbuk();
    }
    public StepCountForSeoulDto getStepCountForGyeongnam(){
        return stepRepository.countStepsForGyeongnam();
    }
    public StepCountForSeoulDto getStepCountForJeju(){
        return stepRepository.countStepsForJeju();
    }*/

    // -------------------------------------마이스토리 관련 -------------------------------------
    public StepCountForAllDto getMyStepCountForAll(String nickname){
        return stepRepository.countMyStepsForAllRegions(nickname);
    }
    public StepCountForSeoulDto getMyStepCountForSeoul(String nickname){
        return stepRepository.countMyStepsForSeoul(nickname);
    }
    public StepCountForBusanDto getMyStepCountForBusan(String nickname){
        return stepRepository.countMyStepsForBusan(nickname);
    }
    public StepCountForGyeonggiDto getMyStepCountForGyeonggi(String nickname){
        return stepRepository.countMyStepsForGyeonggi(nickname);
    }
}
