package com.batch3rd.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements ApplicationRunner {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job helloWorldJob;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Job 가져오기
        String jobName = args.getOptionValues("job.name") != null ? args.getOptionValues("job.name").get(0) : null;
        System.out.println(jobName);
        if(jobName==null || jobName.isEmpty()) {
            System.out.println("No job name provided. Exiting Application");
            return;
        }
        Job job = applicationContext.getBean(jobName, Job.class);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);

    }
}
