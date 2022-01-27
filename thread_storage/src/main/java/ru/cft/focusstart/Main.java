package ru.cft.focusstart;

public class Main {

    public static void main(String[] args) {
        Storage storage = new Storage();
        int workTime = storage.getWorkTime();
        for (int i = 0; i < storage.getConsumerCount(); i++) {
            ThreadConsumer consumer = new ThreadConsumer(storage, storage.generateConsumerId(),
                    storage.getConsumerTime(), workTime);
            consumer.start();
        }
        for (int i = 0; i < storage.getProducerCount(); i++) {
            ThreadProducer producer = new ThreadProducer(storage, storage.generateProducerId(),
                    storage.getProducerTime(), workTime);
            producer.start();
        }
    }
}