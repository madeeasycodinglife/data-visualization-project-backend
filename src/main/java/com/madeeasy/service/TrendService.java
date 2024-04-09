package com.madeeasy.service;

import com.madeeasy.dto.TrendResponseDTO;
import com.madeeasy.entity.Trend;
import com.madeeasy.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendService {

    private final TrendRepository trendRepository;

    public List<TrendResponseDTO> getData() {
        List<Trend> listOfData = this.trendRepository.findAll();
        return listOfData.stream()
                .map(data -> TrendResponseDTO.builder()
                        .endYear(data.getEndYear())
                        .cityLng(data.getCityLng())
                        .cityLat(data.getCityLat())
                        .intensity(data.getIntensity())
                        .sector(data.getSector())
                        .topic(data.getTopic())
                        .insight(data.getInsight())
                        .swot(data.getSwot())
                        .url(data.getUrl())
                        .region(data.getRegion())
                        .startYear(data.getStartYear())
                        .impact(data.getImpact())
                        .added(data.getAdded())
                        .published(data.getPublished())
                        .city(data.getCity())
                        .country(data.getCountry())
                        .relevance(data.getRelevance())
                        .pestle(data.getPestle())
                        .source(data.getSource())
                        .title(data.getTitle())
                        .likelihood(data.getLikelihood())
                        .build())
                .collect(Collectors.toList());
    }
}
