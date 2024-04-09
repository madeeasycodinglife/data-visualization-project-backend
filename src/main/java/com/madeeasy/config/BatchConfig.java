package com.madeeasy.config;

import com.madeeasy.entity.Trend;
import com.madeeasy.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final TrendRepository trendRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Trend> itemReader() throws IOException {
        FlatFileItemReader<Trend> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("/Data.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper()); // Call a separate method to define the line mapper
        return itemReader;
    }

    @Bean
    public LineMapper<Trend> lineMapper() throws IOException {
        DefaultLineMapper<Trend> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(readCsvHeader("Data.csv")); // according to csv file

        BeanWrapperFieldSetMapper<Trend> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Trend.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    private String[] readCsvHeader(String csvFileName) throws IOException {
        Resource resource = new ClassPathResource(csvFileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.readLine().split(",");
        }
    }
    @Bean
    public TrendProcessor processor() {
        return new TrendProcessor();
    }

    @Bean
    public RepositoryItemWriter<Trend> writer() {
        RepositoryItemWriter<Trend> writer = new RepositoryItemWriter<>();
        writer.setRepository(trendRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step importStep() throws IOException {
        return new StepBuilder("csvImport", this.jobRepository)
                .<Trend, Trend>chunk(10, this.platformTransactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() throws IOException {
        return new JobBuilder("importStudents", this.jobRepository)
                .start(importStep())
                .build();
    }
}















