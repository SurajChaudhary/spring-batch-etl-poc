package com.devtalk.batch.etl.config;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BatchJobScheduler {
    private final JobLauncher jobLauncher;
    @Qualifier(value = "etlJob")
    private final Job job;

    @Scheduled(fixedDelay = 60000)
    public void schedule() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        System.out.println("#######################Triggering Job#######################################");
        jobLauncher.run(job, new JobParametersBuilder().addLong("launchTime", System.currentTimeMillis()).toJobParameters());
    }

}
