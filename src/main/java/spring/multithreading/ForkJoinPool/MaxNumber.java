package spring.multithreading.ForkJoinPool;

import java.util.concurrent.RecursiveTask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaxNumber {

    public class Max extends RecursiveTask<Integer> {

        int start, end;
        int[] numArr = {};
        final int THRESHOLD = 5;

        Max(int start, int end, int[] numArr) {
            this.start = start;
            this.end = end;
            this.numArr = numArr;
        }

        @Override
        protected Integer compute() {
            if (end - start < THRESHOLD) {
                int max = Integer.MIN_VALUE;
                for (int i = start; i < end; i++) {
                    max = Math.max(max, numArr[i]);
                }
                return max;
            } else {
                int mid = (start + end) / 2; // Corrected mid calculation
                Max leftMax = new Max(start, mid, numArr);
                Max rightMax = new Max(mid, end, numArr);

                leftMax.fork(); // Fork left task to be processed asynchronously
                int rightMaxResult = rightMax.compute(); // Compute right task in the current thread
                int leftMaxResult = leftMax.join(); // Wait for the left task to finish and get its result

                // Return the maximum value from both sides
                return Math.max(rightMaxResult, leftMaxResult);
            }
        }

    }

    @GetMapping("/fork-join-max")
    public ResponseEntity<?> execute() {
        try {

            int[] numArr = { 4, 3, 5, 12, 3, 55, 33, 40, 45 };
            Max max = new Max(0, numArr.length, numArr);

            int res = max.invoke();
            System.out.println("Max is  " + res);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Failed To Execute Thread ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Thread Executed Successfully ", HttpStatus.OK);
    }

}
