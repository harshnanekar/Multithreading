package spring.multithreading.AsyncMethod;

import java.util.concurrent.CompletableFuture;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

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
        return CompletableFuture.completedFuture("Threas executed " + Thread.currentThread().getName());
    }

}
