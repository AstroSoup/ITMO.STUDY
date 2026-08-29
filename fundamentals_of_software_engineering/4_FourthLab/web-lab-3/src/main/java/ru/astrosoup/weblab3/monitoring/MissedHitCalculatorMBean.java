package ru.astrosoup.weblab3.monitoring;

public interface MissedHitCalculatorMBean {
    double getMissedToTotalHitsInPercents(Long id);
    double getMissedToTotalHitsInPercentsAllUsers();
}
