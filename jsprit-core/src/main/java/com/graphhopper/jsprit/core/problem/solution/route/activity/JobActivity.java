package com.graphhopper.jsprit.core.problem.solution.route.activity;

import java.util.Collection;
import java.util.HashSet;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.SizeDimension;
import com.graphhopper.jsprit.core.problem.job.AbstractJob;
import com.graphhopper.jsprit.core.problem.job.Job;

/**
 * Basic interface of job-activies.
 * <p>
 * <p>
 * A job activity is related to a {@link Job}.
 *
 * @author schroeder
 */
public abstract class JobActivity extends AbstractActivity {

    private AbstractJob job;

    private double operationTime;

    private Collection<TimeWindow> timeWindows;

    private int orderNumber;

    public JobActivity(AbstractJob job, String type, Location location, double operationTime,
                    SizeDimension capacity, Collection<TimeWindow> timeWindows) {
        super(type, location, capacity);
        this.job = job;
        this.operationTime = operationTime;
        this.timeWindows = timeWindows;
    }

    protected JobActivity(JobActivity sourceActivity) {
        super(sourceActivity);
        job = sourceActivity.getJob();
        operationTime = sourceActivity.getOperationTime();
        orderNumber = sourceActivity.getOrderNumber();
        // REMARK - Balage1551 - Do we need to deep copy time window set? I
        // guess we don't.
        if (sourceActivity.timeWindows != null) {
            timeWindows = new HashSet<>(sourceActivity.timeWindows);
        }
    }

    public AbstractJob getJob() {
        return job;
    }

    @Override
    public double getOperationTime() {
        return operationTime;
    }

    @Override
    public String getName() {
        return job.getId() + "." + getType();
    }

    public Collection<TimeWindow> getTimeWindows() {
        return timeWindows;
    }

    public TimeWindow getSingleTimeWindow() {
        if (timeWindows.size() > 1) {
            throw new IllegalArgumentException("More than one time window in. " + this);
        }
        return timeWindows.iterator().next();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((job == null) ? 0 : job.hashCode());
        result = prime * result + orderNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JobActivity other = (JobActivity) obj;
        if (job == null) {
            if (other.job != null) {
                return false;
            }
        } else if (!job.equals(other.job)) {
            return false;
        }
        if (orderNumber != other.orderNumber) {
            return false;
        }
        return true;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

}