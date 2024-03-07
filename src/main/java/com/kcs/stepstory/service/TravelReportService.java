package com.kcs.stepstory.service;

import com.kcs.stepstory.domain.Step;
import com.kcs.stepstory.domain.TravelBody;
import com.kcs.stepstory.domain.TravelReport;
import com.kcs.stepstory.dto.response.TravelReportDto;
import com.kcs.stepstory.dto.response.TravelReportListDto;
import com.kcs.stepstory.repository.StepRepository;
import com.kcs.stepstory.repository.TravelBodyRepository;
import com.kcs.stepstory.repository.TravelReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelReportService {
    private final TravelReportRepository travelReportRepository;
    private final TravelBodyRepository travelBodyRepository;
    private final StepRepository stepRepository;

    public TravelReportListDto getTravelReportList(String province, String city, String district) {
        List<Long> travelReportIds = stepRepository.findByProvinceAndCityAndDistrict(province, city, district)
                .stream()
                .map(Step::getTravelReport)
                .map(TravelReport::getTravelReportId)
                .distinct()
                .collect(Collectors.toList());

        List<TravelBody> filteredBodies = travelBodyRepository.findByTravelReportIdInAndReadPermissionEquals(travelReportIds, 1);

        List<TravelReportDto> reportDtos = filteredBodies.stream()
                .map(TravelBody::getTravelReport)
                .map(TravelReportDto::fromEntity)
                .collect(Collectors.toList());

        return TravelReportListDto.fromEntity(reportDtos);
    }
}
