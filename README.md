# Multithreading in Spring Boot

This repository contains various examples and use cases of **multithreading** and **asynchronous execution** in a **Spring Boot** application. It aims to demonstrate multiple ways to improve performance and handle concurrent tasks efficiently in a Spring Boot environment.

## Table of Contents

- [Introduction](#introduction)
- [Setup](#setup)
- [Technologies Used](#technologies-used)
- [Multithreading Techniques](#multithreading-techniques)
  - [1. Simple Multi-threading](#1-simple-multi-threading)
  - [2. Async with `@Async`](#2-async-with-async)
  - [3. CompletableFuture for Parallel Execution](#3-completablefuture-for-parallel-execution)
  - [4. Using `ExecutorService` for Thread Management](#4-using-executorservice-for-thread-management)
  - [5. Synchronous and Asynchronous Combination](#5-synchronous-and-asynchronous-combination)
  - [6. Using `@Async` with Return Types](#6-using-async-with-return-types)
- [How to Run](#how-to-run)
- [License](#license)

## Introduction

In modern web applications, **multithreading** allows us to handle tasks concurrently, improving performance and responsiveness. This repository provides different approaches to implement multithreading in a **Spring Boot** application, including asynchronous programming, parallel execution, and thread management using standard Java concurrency utilities.

This project demonstrates various patterns you can use in your applications to offload tasks, handle background operations, and improve system scalability.

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-boot-multithreading.git
