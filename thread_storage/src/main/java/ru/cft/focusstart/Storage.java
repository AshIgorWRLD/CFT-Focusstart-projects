package ru.cft.focusstart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;

@Slf4j
public class Storage {
    private static final String PARAMETERS_PROPERTIES_FILE_PATH =
            "src\\main\\resources\\Parameters.properties";
    private static final String STORAGE_SIZE_PROPERTIES_KEY = "storageSize";
    private static final String PRODUCER_COUNT_PROPERTIES_KEY = "producerCount";
    private static final String CONSUMER_COUNT_PROPERTIES_KEY = "consumerCount";
    private static final String PRODUCER_TIME_PROPERTIES_KEY = "producerTime";
    private static final String CONSUMER_TIME_PROPERTIES_KEY = "consumerTime";
    private static final String WORK_TIME_PROPERTIES_KEY = "workTime";
    private static final String TIME_IN_QUEUE_PROPERTIES_KEY = "timeInQueue";
    private static final int SECONDS_COEFFICIENT = 1000;

    private final List<Integer> resourceList;

    private int storageSize;
    @Getter
    private int producerCount;
    @Getter
    private int consumerCount;
    private int producerTime;
    private int consumerTime;
    private int resourceIdCounter;
    private int producerIdCounter;
    private int consumerIdCounter;
    private int workTime;
    private int timeInQueue;

    public Storage() {
        this.takeParametersFromProperties();
        resourceList = new LinkedList<>();
        resourceIdCounter = 1;
        producerIdCounter = 1;
        consumerIdCounter = 1;
    }

    private boolean isFull() {
        return resourceList.size() >= storageSize;
    }

    private boolean isEmpty() {
        return resourceList.size() <= 0;
    }

    public synchronized void addResourceToList(int resourceId, int producerId) {
        long startTime = System.currentTimeMillis() + timeInQueue;
        while (isFull() && System.currentTimeMillis() < startTime) {
            try {
                wait(timeInQueue);
            } catch (InterruptedException e) {
                log.error("error in waiting for empty place in resource list");
            }
        }
        if (System.currentTimeMillis() >= startTime) {
            notify();
            return;
        }
        resourceList.add(resourceId);
        log.info("Producer " + producerId + " has put resource " + resourceId + " in storage");
        log.info("Resources in storage after Producer " + producerId + ": " + resourceList.size());
        notify();
    }

    public synchronized void takeResourceFromList(int consumerId) {
        long startTime = System.currentTimeMillis() + timeInQueue;
        while (isEmpty() && System.currentTimeMillis() < startTime) {
            try {
                wait(timeInQueue);
            } catch (InterruptedException e) {
                log.error("error in waiting for new resources in resource list");
            }
        }
        if (System.currentTimeMillis() >= startTime) {
            notify();
            return;
        }
        int resourceId = resourceList.remove(0);
        log.info("Consumer " + consumerId + " has taken resource " + resourceId);
        log.info("Resources in storage after Consumer " + consumerId + ": " + resourceList.size());
        notify();
    }

    public synchronized int generateResourceId() {
        return resourceIdCounter++;
    }

    public int generateProducerId() {
        return producerIdCounter++;
    }

    public int generateConsumerId() {
        return consumerIdCounter++;
    }

    public int getProducerTime() {
        return producerTime * SECONDS_COEFFICIENT;
    }

    public int getConsumerTime() {
        return consumerTime * SECONDS_COEFFICIENT;
    }

    public int getWorkTime() {
        return workTime * SECONDS_COEFFICIENT;
    }

    private void takeParametersFromProperties() {
        Properties properties = new Properties();
        FileInputStream propFileInputStream = null;

        try {
            propFileInputStream = new FileInputStream(PARAMETERS_PROPERTIES_FILE_PATH);
        } catch (FileNotFoundException var5) {
            log.error("Can't find properties file");
        }

        try {
            properties.load(propFileInputStream);
        } catch (IOException var4) {
            log.error("Can't load information from properties file");
        }

        this.storageSize = Integer.parseInt(properties.getProperty(STORAGE_SIZE_PROPERTIES_KEY));
        this.producerCount = Integer.parseInt(properties.getProperty(PRODUCER_COUNT_PROPERTIES_KEY));
        this.consumerCount = Integer.parseInt(properties.getProperty(CONSUMER_COUNT_PROPERTIES_KEY));
        this.producerTime = Integer.parseInt(properties.getProperty(PRODUCER_TIME_PROPERTIES_KEY));
        this.consumerTime = Integer.parseInt(properties.getProperty(CONSUMER_TIME_PROPERTIES_KEY));
        this.workTime = Integer.parseInt(properties.getProperty(WORK_TIME_PROPERTIES_KEY));
        this.timeInQueue = Integer.parseInt(properties.getProperty(TIME_IN_QUEUE_PROPERTIES_KEY)) *
                SECONDS_COEFFICIENT;
    }
}