ExecutorService in Java (Multithreading)
Introduction
The ExecutorService in Java provides a higher-level replacement for managing and controlling thread execution. It's part of the java.util.concurrent package introduced in Java 5 to manage thread execution more efficiently. It abstracts the complexities of manually creating and managing threads, providing an easy-to-use API to manage task execution.

Why Use ExecutorService?
Thread Pooling: Instead of creating new threads for every task, which can be expensive, the ExecutorService reuses threads from a pool.

Concurrency: Simplifies handling of concurrency by decoupling task submission from the mechanics of how each task will be executed.

Graceful Shutdown: Provides easy-to-use methods like shutdown() and awaitTermination() to gracefully stop the execution of tasks and shut down the executor.

Core Concepts
Executor:

The basic interface for managing and controlling thread execution. It has the method execute(Runnable task) to execute a task asynchronously.

ExecutorService:

ExecutorService is a subinterface of Executor that adds more methods for managing lifecycle (shutdown, waiting for task completion) and retrieving results (through Future).

Thread Pool:

An executor manages a pool of threads, which can be reused to execute multiple tasks. This avoids the overhead of creating a new thread every time a task is submitted.

Common ExecutorService Implementations
1. SingleThreadExecutor
Description: This executor creates a single worker thread to execute tasks sequentially.

Use Case: When you need to ensure that tasks are executed one by one in a single thread.

ExecutorService executor = Executors.newSingleThreadExecutor();
executor.submit(() -> {
    // Your task code
});
executor.shutdown();

2. FixedThreadPool
Description: A thread pool with a fixed number of threads. If all threads are busy, new tasks are queued.

Use Case: When you have a fixed number of tasks and want to limit the number of concurrent threads to manage system resources.

ExecutorService executor = Executors.newFixedThreadPool(3);  // 3 threads
executor.submit(() -> {
    // Your task code
});
executor.shutdown();

3. CachedThreadPool
Description: Creates new threads as needed, but reuses previously constructed threads if available. Threads are terminated after 60 seconds of inactivity.

Use Case: For many short-lived tasks where you don't want the overhead of managing a fixed set of threads.

ExecutorService executor = Executors.newCachedThreadPool();
executor.submit(() -> {
    // Your task code
});
executor.shutdown();

4. ScheduledThreadPool
Description: A thread pool that supports scheduling tasks with delays or periodic execution.

Use Case: Useful for scheduling tasks that need to run at fixed intervals or after a delay (e.g., background maintenance tasks).

ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
executor.schedule(() -> {
    // Your task code
}, 5, TimeUnit.SECONDS);
executor.shutdown();

5. WorkStealingPool
Description: A thread pool that uses work stealing for efficient execution of tasks. Threads can steal work from others if they are idle.

Use Case: When you have tasks that can be divided and parallelized effectively, and you want to balance the load across threads dynamically.

ExecutorService executor = Executors.newWorkStealingPool();
executor.submit(() -> {
    // Your task code
});
executor.shutdown();

ExecutorService Lifecycle
The lifecycle of an ExecutorService follows these basic steps:

Start Execution:

Tasks are submitted to the executor through submit() or execute(). The executor schedules these tasks for execution based on the available threads in the pool.

Shutdown Executor:

The executor can be gracefully shut down by calling shutdown(). This prevents any new tasks from being submitted, but it allows previously submitted tasks to complete.

To forcefully stop the executor, shutdownNow() can be used, though this may leave some tasks unfinished.

Await Termination:

After calling shutdown(), you can use awaitTermination() to block the calling thread until all tasks have completed or the timeout is reached.


executorService.shutdown();
if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
    System.out.println("ExecutorService did not terminate in the specified time.");
}

Working with Tasks

Runnable vs Callable:

Runnable: Represents a task that does not return a result. It is suitable for tasks that do not need to provide feedback after execution.


executor.submit(() -> {
    System.out.println("Task executed");
});

Callable: Represents a task that returns a result and may throw an exception. It is useful when you need to get the result of a computation after task completion.


Future<Integer> result = executor.submit(() -> {
    return 42;  // Task that returns a result
});
Integer value = result.get();  // Get the result