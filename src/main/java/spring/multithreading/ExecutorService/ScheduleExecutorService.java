package spring.multithreading.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleExecutorService {

    // Method with Schedule Thread Pool
    @GetMapping("/scheduled-thread")
    public ResponseEntity<?> executeSchedulePool() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

            for (int i = 0; i < 5; i++) {
                scheduler.schedule(() -> {
                    System.out.println("Thread Executed Successfully " + Thread.currentThread().getName());
                }, 10, TimeUnit.SECONDS);
            }

            scheduler.shutdown();

            if (!scheduler.awaitTermination(30, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to execute tasks at fixed rate
    @GetMapping("/scheduled-fix-thread")
    public ResponseEntity<?> executeFixedSchedulePool() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

            Runnable task = () -> {
                try {
                    Thread.sleep(5000);
                    System.out.println(
                        "Thread started by " + Thread.currentThread().getName() + " at " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };

            // Schedule task at fixed rate: Initial delay 2 seconds, runs every 5 seconds
            scheduler.scheduleAtFixedRate(task, 2, 5, TimeUnit.SECONDS);

            // Schedule shutdown after 30 seconds
            scheduler.schedule(() -> {
                System.out.println("Shutting down scheduler...");
                scheduler.shutdown();
            }, 15, TimeUnit.SECONDS);

            // Wait for the shutdown to complete
            if (!scheduler.awaitTermination(35, TimeUnit.SECONDS)) {
                System.out.println("Forcing Shutdown...");
                scheduler.shutdownNow();
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to execute tasks at fixed rate
    @GetMapping("/scheduled-fix-delay")
    public ResponseEntity<?> executeFixedSchedulePoolDelay() {
        try {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

            Runnable task = () -> {
                try {
                    Thread.sleep(5000);
                    System.out.println("Thread started by " + Thread.currentThread().getName() + " at "
                            + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            };

            // Schedule task at fixed rate: Initial delay 2 seconds, runs every 5 seconds
            scheduler.scheduleWithFixedDelay(task, 2, 5, TimeUnit.SECONDS);

            // Schedule shutdown after 30 seconds
            scheduler.schedule(() -> {
                System.out.println("Shutting down scheduler...");
                scheduler.shutdown();
            }, 15, TimeUnit.SECONDS);

            // Wait for the shutdown to complete
            if (!scheduler.awaitTermination(35, TimeUnit.SECONDS)) {
                System.out.println("Forcing Shutdown...");
                scheduler.shutdownNow();
            }

            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
