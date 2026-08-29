package ru.astrosoup.weblab3.monitoring;

import ru.astrosoup.weblab3.DTOs.hit.AreaHitResponse;

public interface HitCheckerMBean {
    long getTotalHits(Long id);
    long getMissedHits(Long id);
    long getTotalHitsAllUsers();
    long getMissedHitsAllUsers();
    void addPoint(AreaHitResponse hit, Long id);
}
