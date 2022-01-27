package ru.cft.focusstart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadProducer extends Thread {

    private final Storage storage;
    private final int id;
    private final int sleepTime;
    private final int workTime;

    public ThreadProducer(Storage storage, int id, int sleepTime, int workTime) {
        this.storage = storage;
        this.id = id;
        this.sleepTime = sleepTime;
        this.workTime = workTime;
    }

    public void run() {
        long endTime = System.currentTimeMillis() + workTime;
        while (System.currentTimeMillis() < endTime) {
            int resourceId = storage.generateResourceId();
            storage.addResourceToList(resourceId, id);

            log.info("Producer " + id + " has produced resource " + resourceId);
            log.info("Producer " + id + " went to sleep");
            try {
                sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("Producer " + id + " can't go to sleep");
            }
            log.info("Producer " + id + " woke up");
        }
        log.info("Producer " + id + " ended work");
    }
}