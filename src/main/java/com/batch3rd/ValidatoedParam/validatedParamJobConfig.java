package com.batch3rd.ValidatoedParam;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * desc : 파일 이름 파라미터 전달 그리고 검증
 * run : --job.name=validatedParamJob -fileName=test.csv
 */
@Configuration
public class validatedParamJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public validatedParamJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job validatedParamJob(Step validatedParamStep) {
        return new JobBuilder("validatedParamJob", jobRepository)
                .start(validatedParamStep)
                .build();
    }

    @Bean
    public Step validatedParamStep(Tasklet validatedParamTasklet) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(validatedParamTasklet, transactionManager)
                .build();
    }

    @Bean
    public Tasklet validatedParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(fileName);
                System.out.println("validated Param Tasklet");
                return RepeatStatus.FINISHED;
            }
        };
    }

}