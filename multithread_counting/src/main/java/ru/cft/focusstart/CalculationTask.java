package ru.cft.focusstart;

public class CalculationTask {

    public double getAnswer(int threadNumber, long userNumber, int startIdx) {
        double sumPart = 0;
        for (long i = startIdx; i < userNumber; i += threadNumber) {
            sumPart += 1 / Math.pow(2, i);
        }

        return sumPart;
    }
}
