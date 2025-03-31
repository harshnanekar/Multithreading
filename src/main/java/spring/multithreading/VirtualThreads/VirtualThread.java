package spring.multithreading.VirtualThreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirtualThread {

    @GetMapping("/virtual-thread")
    public ResponseEntity<?> virtualThread() {
        try {
            Thread.ofVirtual()
                    .name("Virtual Thread 1")
                    .start(() -> {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread in running mode " + Thread.currentThread().getName());
                    });

        } catch (Exception e) {
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);
    }

    @GetMapping("/executor-virtual-thread")
    public ResponseEntity<?> virtaulThread1() {
        try {
            ExecutorService executorService = Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name("Virtual Thread", 0).factory()
            );

            executorService.submit(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread in running mode " + Thread.currentThread().getName());
            });

        } catch (Exception e) {
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);
    }

}
