package eu.cyfronoid.core.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

import eu.cyfronoid.core.types.Counter;
import eu.cyfronoid.core.util.Constants;

public class ThreadsDump extends AbstractTask {
    private static final Logger logger = Logger.getLogger(ThreadsDump.class);

    private static final int REPEAT_EVERY_N_SECONDS = 60; //TODO from properties

    private Map<Thread.State, Counter> threadsNumberByState;

    public ThreadsDump() {
        createCounters();
    }

    private void createCounters() {
        Map<Thread.State, Counter> initializingMap = new HashMap<>();
        for(Thread.State state : Thread.State.values()) {
            initializingMap.put(state, new Counter());
        }
        threadsNumberByState = ImmutableMap.copyOf(initializingMap);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Set<Thread> threads =  Thread.getAllStackTraces().keySet();
        resetCounters();
        threads.forEach(t -> threadsNumberByState.get(t.getState()).inc());
        logger.debug(getMessage(threads.size()));
    }

    private void resetCounters() {
        for(Thread.State state : Thread.State.values()) {
            threadsNumberByState.get(state).reset();
        }
    }

    private String getMessage(int totalNumberOfThreads) {
        StringBuilder message = new StringBuilder("Threads stats:\nTotal number of threads: ")
                .append(totalNumberOfThreads).append(Constants.NEW_LINE.value);
        appendThreadsByState(message);
        return message.toString();
    }

    private void appendThreadsByState(StringBuilder message) {
        threadsNumberByState.forEach((s, c) -> {
            message.append("State ").append(s).append(": ").append(c.getValue())
                    .append(Constants.NEW_LINE.value);
        });
    }

    @Override
    public Trigger getTrigger() {
        SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
        simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + REPEAT_EVERY_N_SECONDS));
        simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        simpleTrigger.setRepeatInterval(REPEAT_EVERY_N_SECONDS*1000);
        simpleTrigger.setName("ThreadsDumpTrigger");
        return simpleTrigger;
    }

    public static void main(String args[]) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new Scheduler();
        scheduler.startTask(new ThreadsDump());
        scheduler.startTask(new TestTask());
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(130000);
                } catch (InterruptedException e) {
                    System.out.println(Throwables.getStackTraceAsString(e));
                }
                System.out.println("Hello from a thread!");
            }

        });
        thread.start();
        Thread.sleep(150000);
        scheduler.stop();
    }

    public static class TestTask extends AbstractTask {

        @Override
        public void execute(JobExecutionContext context)
                throws JobExecutionException {
            System.out.println("ASDF");
        }

        @Override
        public Trigger getTrigger() {
            SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
            simpleTrigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
            simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
            simpleTrigger.setRepeatInterval(1000);
            simpleTrigger.setName("asdfasdfadsf");
            return simpleTrigger;
        }
    }

}
