package me.hjjang.batchexample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExampleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job exampleJob() {
        return jobBuilderFactory.get("exampleJob")
                .start(exampleStep1(null))
                .next(exampleStep2(null))
                .build();
    }

    @Bean
    @JobScope
    public Step exampleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("exampleStep1")
                .tasklet((contribution, chunkContext) -> {
//                    throw new IllegalArgumentException("step1 에서 실패합니다.");
                    log.info(">>>> This is Step1");
                    log.info(">>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    @JobScope
    public Step exampleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("exampleStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> This is Step2");
                    log.info(">>>> requestDate = {}", requestDate);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
