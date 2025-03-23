package spring.multithreading.ExecutorService;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutorServiceImpl {

    // Method with Return Type
    @GetMapping("/execute-service-test")
    public ResponseEntity<?> executeTest() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(3);

            for (int i = 0; i < 6; i++) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(10000);
                        System.out.println("Thread executed successfully " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        executorService.shutdownNow();
                    }
                });
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Time Exceeded");
                executorService.shutdownNow();
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method without Return Type
    @GetMapping("/execute-service-test1")
    public ResponseEntity<?> executeTest1() {
        try {
            // ExecutorService executorService = Executors.newFixedThreadPool(3);

            // ExecutorService executorService = new ThreadPoolExecutor(3,
            // 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new
            // ThreadPoolExecutor.CallerRunsPolicy());

            ExecutorService executorService = Executors.newCachedThreadPool();

            Future<String> result = executorService.submit(() -> {
                try {
                    Thread.sleep(10000);
                    return "Thread completed";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    executorService.shutdownNow();
                    return "Thread falied";
                }
            });

            result.get();
            System.out.println(result.resultNow());
            executorService.shutdown();

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method with Single Thread Executor
    @GetMapping("/single-thread-executor")
    public ResponseEntity<?> executeTest2() {
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            for (int i = 0; i <= 5; i++) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Thread executed " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            executorService.shutdown();

            // Wait for all tasks to finish
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                return new ResponseEntity<>("Timeout occurred. Not all tasks completed.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method with Semaphore Usage
    @GetMapping("/semaphore")
    public ResponseEntity<?> executeSemaphore() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            Semaphore semaphore = new Semaphore(3);

            for (int i = 0; i <= 10; i++) {
                executorService.submit(() -> {
                    try {
                        semaphore.acquire();
                        Thread.sleep(5000);
                        System.out.println("Thread executed " + Thread.currentThread().getName() + Thread.currentThread().getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                });
            }

            executorService.shutdown();

            // Wait for all tasks to finish
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                return new ResponseEntity<>("Timeout occurred. Not all tasks completed.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
