package com.madeeasy.controller;

import com.madeeasy.dto.TrendResponseDTO;
import com.madeeasy.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/trend-service")
@RequiredArgsConstructor
public class TrendController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final TrendService trendService;

    @PostMapping(path = "/start")
    public void importCsvToDBJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        this.jobLauncher.run(this.job, jobParameters);
    }

    @GetMapping(path = "/get-all-data")
    public ResponseEntity<?> getData() {
        List<TrendResponseDTO> listOfData = this.trendService.getData();
        return ResponseEntity.status(HttpStatus.OK).body(listOfData);
    }
}
