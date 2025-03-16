package spring.multithreading.AsyncMethod;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AsyncController {

  @Autowired
  private AsyncConfig asyncConfig;

  //Method for single thread
  @GetMapping("/single-thread")
  public @ResponseBody ResponseEntity<?> testController1() {
    try {
      
      //Calling without return type
      // asyncConfig.asyncTest();
      
      //Calling with return type
      CompletableFuture<String> completedFuture = asyncConfig.asyncReturnTest();
  
      //This will block the thread until it completes
      // completedFuture.get();

      // return new ResponseEntity<>(completedFuture.resultNow(), HttpStatus.OK);

      return new ResponseEntity<>("Thread executed successfully", HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>("Thread failed to execute", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //Method for parallel threads
  @GetMapping("/parallel-thread")
  public @ResponseBody ResponseEntity<?> testController2() {
    try {
      
      //Calling with return type
      CompletableFuture<String> completedFuture = asyncConfig.asyncReturnTest();
      CompletableFuture<String> completedFuture1 = asyncConfig.asyncReturnTest();
     
      return new ResponseEntity<>("Thread executed successfully", HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>("Thread failed to execute", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  } 

   //Method for custom threads
   @GetMapping("/custom-thread")
   public @ResponseBody ResponseEntity<?> testController3() {
     try {
       
       asyncConfig.asyncThreadPool();
       asyncConfig.asyncThreadPool();
      //  asyncConfig.asyncThreadPool();
      //  asyncConfig.asyncThreadPool();
      //  asyncConfig.asyncThreadPool();

       return new ResponseEntity<>("Thread executed successfully", HttpStatus.OK);
 
     } catch (Exception e) {
       e.printStackTrace();
       return new ResponseEntity<>("Thread failed to execute", HttpStatus.INTERNAL_SERVER_ERROR);
     }
   } 

}
