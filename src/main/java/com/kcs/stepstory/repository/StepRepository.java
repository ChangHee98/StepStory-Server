package com.kcs.stepstory.repository;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.domain.StepId;
import com.kcs.stepstory.domain.User;
import com.kcs.stepstory.repository.custom.StepRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long>, StepRepositoryCustom {
    List<Step> findStepsByProvinceAndCityAndDistrict(String province, String city, String district);
    List<Step> findStepsByProvinceAndCityAndDistrictAndUser(String province, String city, String district, User user);
}
