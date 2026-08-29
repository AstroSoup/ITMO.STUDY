package ru.astrosoup.weblab3.monitoring;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.ejb.Singleton;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.logging.Logger;
@Startup
@Singleton
public class MissedHitCalculator implements MissedHitCalculatorMBean {

    private static final Logger log = Logger.getLogger(MissedHitCalculator.class.getName());

    @Inject
    HitCheckerMBean hitChecker;

    @PostConstruct
    public void register() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("ru.astrosoup.weblab3:type=MissedHitCalculator");
            mbs.registerMBean(this, name);
            log.info(">>> MissedHitCalculator registered with JMX");
        } catch (Exception e) {
            log.severe(">>> MissedHitCalculator JMX registration failed: " + e.getMessage());
        }
    }

    @PreDestroy
    public void unregister() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.unregisterMBean(new ObjectName("ru.astrosoup.weblab3:type=MissedHitCalculator"));
        } catch (Exception e) {
            log.severe(">>> MissedHitCalculator JMX unregistration failed: " + e.getMessage());
        }
    }

    @Override
    @Lock(LockType.READ)
    public double getMissedToTotalHitsInPercentsAllUsers() {
        long total = hitChecker.getTotalHitsAllUsers();
        if (total == 0) return 0.0;
        return (double) hitChecker.getMissedHitsAllUsers() / total * 100;
    }

    @Override
    @Lock(LockType.READ)
    public double getMissedToTotalHitsInPercents(Long id) {
        long total = hitChecker.getTotalHits(id);
        if (total == 0) return 0.0;  // also fix the division by zero you currently have
        return (double) hitChecker.getMissedHits(id) / total * 100;
    }
}