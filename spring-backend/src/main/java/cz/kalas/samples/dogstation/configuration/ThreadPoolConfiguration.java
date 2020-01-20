package cz.kalas.samples.dogstation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfiguration implements AsyncConfigurer {

    @Bean
    public TaskExecutor applicationTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("myThread");
        taskExecutor.initialize();
        return taskExecutor;

    }


}
