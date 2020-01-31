package com.hyecheon.msscbeerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hyecheon
 * @date 2020-01-30
 */
@EnableAsync
@EnableScheduling
@Configuration
public class TaskConfig {

  @Bean
  TaskExecutor taskExecutor(){
    return new SimpleAsyncTaskExecutor();
  }
}
