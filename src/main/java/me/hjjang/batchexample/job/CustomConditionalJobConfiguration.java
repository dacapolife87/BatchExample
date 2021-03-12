package me.hjjang.batchexample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customConditionalJob() {
        return jobBuilderFactory.get("customConditionalJob")
            .start(customJobStep1())
                .on("FAILED")
                .end()
            .from(customJobStep1())
                .on("COMPLETED WITH SKIPS")
                .to(customJobStep2())
//                .end()
            .from(customJobStep1())
                .on("*")
                .to(customJobStep3())
            .end()
            .build();
    }

    @Bean
    public Step customJobStep1() {
        return stepBuilderFactory.get("customJobStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is customJobStep Step1");
//                    contribution.setExitStatus(new ExitStatus("COMPLETED WITH SKIPS"));
//                    contribution.setExitStatus(new ExitStatus("FAILED"));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step customJobStep2() {
        return stepBuilderFactory.get("customJobStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is customJobStep Step2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step customJobStep3() {
        return stepBuilderFactory.get("customJobStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is customJobStep Step3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
