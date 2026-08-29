package ru.astrosoup.weblab3.monitoring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.*;
import ru.astrosoup.weblab3.DTOs.hit.AreaHitResponse;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class HitChecker extends NotificationBroadcasterSupport implements HitCheckerMBean {

    private static final Logger log = Logger.getLogger(HitChecker.class.getName());

    private final Map<Long, Long> totalPointsById  = new HashMap<>();
    private final Map<Long, Long> missedPointsById = new HashMap<>();
    private final AtomicLong sequenceNumber = new AtomicLong(1L);

    @PostConstruct
    public void register() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("ru.astrosoup.weblab3:type=HitChecker");
            mbs.registerMBean(this, name);
            log.info(">>> HitChecker registered with JMX");
        } catch (Exception e) {
            log.severe(">>> HitChecker JMX registration failed: " + e.getMessage());
        }
    }

    @PreDestroy
    public void unregister() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("ru.astrosoup.weblab3:type=HitChecker");
            mbs.unregisterMBean(name);
        } catch (Exception e) {
            log.severe(">>> HitChecker JMX unregistration failed: " + e.getMessage());
        }
    }


    @Override
    @Lock(LockType.READ)
    public long getTotalHitsAllUsers() {
        return totalPointsById.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    @Lock(LockType.READ)
    public long getMissedHitsAllUsers() {
        return missedPointsById.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    @Lock(LockType.READ)
    public long getTotalHits(Long id) {
        return totalPointsById.getOrDefault(id, 0L);
    }

    @Override
    @Lock(LockType.READ)
    public long getMissedHits(Long id) {
        return missedPointsById.getOrDefault(id, 0L);
    }

    @Lock(LockType.WRITE)
    public void addPoint(AreaHitResponse hit, Long id) {
        totalPointsById.merge(id, 1L, Long::sum);

        if (!hit.isHit()) {
            missedPointsById.merge(id, 1L, Long::sum);
        }

        if (Math.abs(hit.getX()) > 5 || Math.abs(hit.getY()) > 5) {
            sendNotification(new Notification(
                    "ru.astrosoup.weblab3.monitoring.point-out-of-bounds",
                    this,
                    sequenceNumber.getAndIncrement(),
                    System.currentTimeMillis(),
                    String.format("The hit (%d, %f) was out of bounds for the displayed field.",
                            hit.getX(), hit.getY())
            ));
        }
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        return new MBeanNotificationInfo[]{
                new MBeanNotificationInfo(
                        new String[]{ "ru.astrosoup.weblab3.monitoring.point-out-of-bounds" },
                        Notification.class.getName(),
                        "Point is outside the shown plane"
                )
        };
    }
}