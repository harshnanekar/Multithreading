package spring.multithreading.CompletableFuture;

import java.util.concurrent.CompletableFuture;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompletableFutureImpl {

    public static int calculate(int num1, int num2) {
        return num1 + num2;
    }

    @GetMapping("/completable")
    public ResponseEntity<?> executeTask() {

        try {
            int sum1 = calculate(3, 4);
            System.out.println("Calculation of sum 1 is " + sum1);

            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            }).thenApply((result) -> {
                System.out.println("Complete future " + result);
                return result + 5;
            }).thenAccept(result -> System.out.println("Result in accept " + result));

            int sum2 = calculate(5, 5);
            System.out.println("Calculation of sum 2 is " + sum2);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

    }

    @GetMapping("/completable-sum")
    public ResponseEntity<?> executeTask1() {

        try {
            int sum1 = calculate(3, 4);
            System.out.println("Calculation of sum 1 is " + sum1);

            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculate(6, 6);
                System.out.println("Runned Async 1");
            }).thenRunAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculate(6, 6);
                System.out.println("Runned Async 2");
            })
                    .exceptionally(ex -> {
                        System.err.println("Exception occurred: " + ex.getMessage());
                        return null;
                    });

            int sum2 = calculate(5, 5);
            System.out.println("Calculation of sum 2 is " + sum2);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

    }

    @GetMapping("/completable-combine")
    public ResponseEntity<?> executeTask2() {

        try {
            int sum1 = calculate(3, 4);
            System.out.println("Calculation of sum 1 is " + sum1);

            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture<Integer> res = completableFuture.thenCombine(completableFuture1, (result1, result2) -> {
                return result1 * result2;
            });

            res.thenAccept((result) -> System.out.println("Result of combine is " + result));

            int sum2 = calculate(5, 5);
            System.out.println("Calculation of sum 2 is " + sum2);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

    }

    @GetMapping("/completable-all-of")
    public ResponseEntity<?> executeTask3() {

        try {
            int sum1 = calculate(3, 4);
            System.out.println("Calculation of sum 1 is " + sum1);

            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture.allOf(completableFuture, completableFuture1)
                    .exceptionally(ex -> {
                        System.err.println("Exception occurred: " + ex.getMessage());
                        return null;
                    })
                    .thenRun(() -> {
                        System.out.println("Sum of first is " + completableFuture.join());
                        System.out.println("Sum of second is " + completableFuture1.join());
                    });

            int sum2 = calculate(5, 5);
            System.out.println("Calculation of sum 2 is " + sum2);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

    }

    @GetMapping("/completable-any-of")
    public ResponseEntity<?> executeTask4() {

        try {
            int sum1 = calculate(3, 4);
            System.out.println("Calculation of sum 1 is " + sum1);

            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000);
                    throw new RuntimeException("Error in task 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return calculate(6, 6);
            });

            CompletableFuture.anyOf(completableFuture, completableFuture1)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.out.println("Error " + ex.getMessage());
                        } else {
                            System.out.println("Sum is " + result);
                        }
                    });

            int sum2 = calculate(5, 5);
            System.out.println("Calculation of sum 2 is " + sum2);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);

    }

}
