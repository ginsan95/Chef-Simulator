
public class Job implements Comparable<Job>{
	
	private int jobID;
	private int time;
	private int priority;
	private int attempt;
	
	public Job(int id, int t)
	{
		jobID = id;
		time = t;
		priority = time;
		attempt = 1;
	}
	
	public void setJobID(int id)
	{
		if(id>=0)
		{
			jobID = id;
		}
	}
	
	public int getJobID()
	{
		return jobID;
	}
	
	public void setTime(int t)
	{
		if(t>=0)
		{
			time = t;
		}
	}
	
	public int getTime()
	{
		return time;
	}
	
	public void setPriority(int p)
	{
		priority = p;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public void setAttempt(int num)
	{
		attempt = num;
	}
	
	public int getAttempt()
	{
		return attempt;
	}

	@Override
	public int compareTo(Job job2) {
		if(this.getPriority()<job2.getPriority())
		{
			return -1;
		}
		if(this.getPriority()>job2.getPriority())
		{
			return 1;
		}
		return 0;
	}

}
