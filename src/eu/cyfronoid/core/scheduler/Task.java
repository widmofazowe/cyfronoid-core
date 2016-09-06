package eu.cyfronoid.core.scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface Task extends Job {
    Trigger getTrigger();
    JobDetail getJobDetail();
}
