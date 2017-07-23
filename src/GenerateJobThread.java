import java.util.Random;

public class GenerateJobThread implements Runnable{
	
	private int jobID;
	private JobQueue queue;
	Random rand = new Random();
	
	public GenerateJobThread(JobQueue q)
	{
		queue = q;
		jobID = rand.nextInt(1000);
	}
	
	@Override
	public void run()
	{
		while(!queue.getEnd())
		{
			queue.add(new Job(jobID++, rand.nextInt(14)+5));
			
			try
			{
				Thread.sleep(3000);
			} catch(InterruptedException e){}
		}
	}

}
