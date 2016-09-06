package eu.cyfronoid.core.scheduler;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.impl.JobDetailImpl;

public abstract class AbstractTask implements Job {
    public abstract Trigger getTrigger();

    public JobDetail getJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(getClass().getSimpleName());
        jobDetail.setJobClass(getClass());
        return jobDetail;
    }
}
