package spring.multithreading.ExecutorService;

import java.util.concurrent.Executor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Execute {

    @GetMapping("/execute-test")
    public ResponseEntity<?> executeTest() {
        try {
            Executor executor = command -> new Thread(command).start();
            executor.execute(() -> { 
                try {
                    Thread.sleep(10000);
                    System.out.println("Thread exceuted " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
            return new ResponseEntity<>("Thread Executed Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("failed To Execute Thread", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
