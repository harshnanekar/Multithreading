package spring.multithreading.AsyncMethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    //Custom thread pool
    @Bean(name = "threadPool")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(1);
        threadPool.setMaxPoolSize(2);
        threadPool.setQueueCapacity(2);
        threadPool.setThreadNamePrefix("test");
        return threadPool;
    }

    // Method without return type
    @Async
    public void asyncTest() throws InterruptedException {
       Thread.sleep(10000);
       System.out.println("Thread executed " + Thread.currentThread().getName());
    }

    //Method with return type
    @Async
    public CompletableFuture<String> asyncReturnTest() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("Thread executed " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Thread executed " + Thread.currentThread().getName());
    }

    @Async("threadPool")
    public void asyncThreadPool () throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Thread executed " + Thread.currentThread().getName());
    }

}
