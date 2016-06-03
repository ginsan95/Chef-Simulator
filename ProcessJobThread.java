import javax.swing.JLabel;

public class ProcessJobThread implements Runnable{
	
	private final int MAX_TIME = 8;
	private JobQueue queue;
	private JLabel myLabel;
	private int chefNum;
	private int[] attemptCount;
	
	public ProcessJobThread(JobQueue q, int num, JLabel jl)
	{
		queue = q;
		chefNum = num;
		myLabel = jl;
		attemptCount = new int[10];
	}
	
	@Override
	public void run()
	{
		while(!queue.getEnd())
		{
			Job job = queue.remove();
			int tempTime = job.getTime();
			
			for(int i=0; i<tempTime; i++)
			{	
				int time = job.getTime();
				job.setTime(--time);
				
				myLabel.setText(String.format("<html>Chef %d<br>JobID: %d<br>Time: %d<br>Time left: %d</html>",
						chefNum, job.getJobID(), tempTime, time));
				
				if(queue.getEnd())
				{
					break;
				}
				
				try
				{
					Thread.sleep(1000);
				}catch (InterruptedException e){}
			
				if(i>=MAX_TIME-1 && time>0)
				{
					int tempAttempt = job.getAttempt();
					job.setAttempt(++tempAttempt);
					if(job.getPriority()>time)
					{
						job.setPriority(time);
					}
					queue.add(job);
					break;
				}
			}
			if(job.getTime()<=0)
			{
				attemptCount[job.getAttempt()-1]++;
			}
			myLabel.setText("<html>Chef "+ chefNum + "<br>Waiting for job</html>");
		}
	}
	
	public int getCount(int attempt)
	{
		return attemptCount[attempt-1];
	}
	
	public int getTotalCount()
	{
		int sum = 0;
		
		for(int i=0; i<attemptCount.length; i++)
		{
			sum += attemptCount[i];
		}
		
		return sum;
	}
}
