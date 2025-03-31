package spring.multithreading.ForkJoinPool;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForkJoinExample {

    public class Sum extends RecursiveTask<Integer> {

        int start;
        int end;
        int[] numarr;
        final int THRESHOLD = 4;

        Sum(int start, int end, int[] numarr) {
            this.start = start;
            this.end = end;
            this.numarr = numarr;
        }

        @Override
        protected Integer compute() {
        
            if (end - start <= THRESHOLD) {
                System.out.println("inside normal " + Arrays.toString(Arrays.copyOfRange(numarr, start, end)));
                int sum = 0;

                for (int i = start; i < end; i++) {
                    sum += numarr[i];
                }

                return sum;
            } else {
                System.out.println("inside fork join " + Arrays.toString(Arrays.copyOfRange(numarr, start, end)));
                int mid = (start + end) / 2;
                Sum leftTask = new Sum(start, mid, numarr);
                Sum rightTask = new Sum(mid, end, numarr);
                leftTask.fork();

                int rightTaskResult = rightTask.compute();
                int leftTaskResult = leftTask.join();

                return leftTaskResult + rightTaskResult;
            }
        }

    }

    @GetMapping("/fork-join")
    public ResponseEntity<?> execute() {
        try {

            int[] numArr = { 4, 3, 5, 12, 3, 55, 33, 40, 45 };
            Sum sum = new Sum(0, numArr.length, numArr);

            int res = sum.invoke();
            System.out.println("Sum of total is " +  res);

        } catch (Exception e) {
            return new ResponseEntity("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);
    }

}