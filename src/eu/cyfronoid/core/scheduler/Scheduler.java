package eu.cyfronoid.core.scheduler;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.impl.DirectSchedulerFactory;

import com.google.common.base.Optional;

public class Scheduler {
    private static final Logger logger = Logger.getLogger(Scheduler.class);

    private final int MAX_SCHEDULED_TASKS = 10; //TODO: from properties

    private Optional<org.quartz.Scheduler> scheduler;

    Scheduler() {
        start();
    }

    private void start() {
        try {
            DirectSchedulerFactory.getInstance().createVolatileScheduler(MAX_SCHEDULED_TASKS);
            scheduler = Optional.of(DirectSchedulerFactory.getInstance().getScheduler());
            if(scheduler.isPresent()) {
                scheduler.get().start();
                logger.info("Scheduler has started.");
            }
        } catch (SchedulerException e) {
            scheduler = Optional.absent();
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            scheduler().shutdown();
            logger.info("Scheduler shutdown.");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTask(AbstractTask task) {
        try {
            scheduler().scheduleJob(task.getJobDetail(), task.getTrigger());
            logger.info("Added task " + task.getClass().getSimpleName() + " to scheduler.");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private org.quartz.Scheduler scheduler() {
        if(scheduler.isPresent()) {
            return scheduler.get();
        }
        throw new RuntimeException("Cannot use scheduler because it cannot be obtained from factory.");
    }

}
