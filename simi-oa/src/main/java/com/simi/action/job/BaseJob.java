package com.simi.action.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
	}

}
