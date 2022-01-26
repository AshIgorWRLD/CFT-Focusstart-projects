package ru.cft.focusstart;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class MyRow {
    private static final int THREAD_NUMBER = 16;
    private final long userNumber;

    public MyRow(long userNumber) {
        this.userNumber = userNumber;
    }

    public double countRow() throws ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUMBER);
        CalculationTask calculationTask = new CalculationTask();

        List<Future<Double>> futures = new ArrayList<>();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            final int idx = i;
            futures.add(CompletableFuture.supplyAsync(() ->
                    calculationTask.getAnswer(THREAD_NUMBER, userNumber, idx), threadPool));
        }
        double result = 0;
        for (Future<Double> future : futures) {
            try {
                result += future.get();
            } catch (InterruptedException e) {
                log.error("Interrupted exception", e);
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                log.error("error in counting", e);
                threadPool.shutdown();
                throw e;
            }
        }
        threadPool.shutdown();
        return result;
    }
}
