package ru.cft.focusstart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadConsumer extends Thread {

    private final Storage storage;
    private final int id;
    private final int sleepTime;
    private final int workTime;

    public ThreadConsumer(Storage storage, int id, int sleepTime, int workTime) {
        this.storage = storage;
        this.id = id;
        this.sleepTime = sleepTime;
        this.workTime = workTime;
    }

    public void run() {
        log.info("Consumer " + id + " started");
        long endTime = System.currentTimeMillis() + workTime;
        while (System.currentTimeMillis() < endTime) {

            storage.takeResourceFromList(id);

            log.info("Consumer " + id + " went to sleep");
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("Consumer " + id + " can't go to sleep");
            }
            log.info("Consumer " + id + " woke up");
        }
        log.info("Consumer " + id + " ended work");
    }
}