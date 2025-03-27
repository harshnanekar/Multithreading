package spring.multithreading.ExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkStealingPool {

    @GetMapping("/work-stealing")
    public ResponseEntity<?> example1() {
        // To view CPU cores
        System.out.println("CPU Cores " + Runtime.getRuntime().availableProcessors());

        ExecutorService executorService = Executors.newWorkStealingPool();

        try {

            for (int i = 0; i < 10; i++) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(5000);
                        System.out.println("Thread started by " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
