package com.dc.iotspark.utils;

/**
 * Reference Link : https://www.callicoder.com/java-callable-and-future-tutorial/
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentExecutionUtils {

    public static void main(String[] args) {

        sequencellyCallService();
        System.out.println("#####################################");
        futureLamba();
        System.out.println("#####################################");
        executorServiceNoLambda();
        System.out.println("#####################################");
        executorServiceLambda();

    }

    public static void sequencellyCallService() {
        long start = System.currentTimeMillis();
        String firstResponse = callCustomerRef(1);
        String secondResponse = callCustomerRef(2);
        String threeResponse = callCustomerRef(3);
        System.out.println("API1 Response =>" + firstResponse);
        System.out.println("API2 Response =>" + secondResponse);
        System.out.println("API3 Response =>" + threeResponse);
        long end = System.currentTimeMillis();
        System.out.println("Sequence call  response time = " + (end - start) + " millis");

    }

    public static void futureLamba() {

        try {
            long start = System.currentTimeMillis();
            ExecutorService executorService = Executors.newCachedThreadPool();
            Future<String> firstCallFuture = executorService.submit(() -> callCustomerRef(1));
            Future<String> secondCallFuture = executorService.submit(() -> callCustomerRef(2));
            Future<String> threeCallFuture = executorService.submit(() -> callCustomerRef(3));
            String firstResponse = firstCallFuture.get();
            String secondResponse = secondCallFuture.get();
            String threeResponse = secondCallFuture.get();
            System.out.println("API1 Response =>" + firstResponse);
            System.out.println("API2 Response =>" + secondResponse);
            System.out.println("API3 Response =>" + threeResponse);
            long end = System.currentTimeMillis();
            System.out.println("Future  response time = " + (end - start) + " millis");
            executorService.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executorServiceNoLambda() {
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                // Calling user id 1: sri
                return callCustomerRef(1);
            }
        });

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                //// Calling user id 1: welcome
                return callCustomerRef(2);
            }
        });

        callables.add(new Callable<String>() {
            public String call() throws Exception {
                //// Calling user id 1: Maxis
                return callCustomerRef(3);
            }
        });

        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("ExecutorServiceNoLambda  response time = " + (end - start) + " millis");
        service.shutdown();
    }

    public static void executorServiceLambda() {
        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newCachedThreadPool();
        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        callables.add(() -> {
            return callCustomerRef(1);
        });
        callables.add(() -> {
            return callCustomerRef(2);
        });
        callables.add(() -> {
            return callCustomerRef(3);
        });

        try {
            List<Future<String>> futures = service.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Callable Lambda   response time = " + (end - start) + " millis");
        service.shutdown();
    }

    public static String callCustomerRef(Integer id) {
        try {
            URL url = new URL("http://localhost:8787/api/v1/getCustomerRef/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(content));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}