
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.swing.table.DefaultTableModel;

public class JobQueue {
	
	private PriorityQueue<Job> queue;
	private boolean end;
	private int jobCount;
	private DefaultTableModel myTableModel;
	
	public JobQueue()
	{
		queue = new PriorityQueue<Job>();
		end = false;
		jobCount = 0;
	}
	
	public JobQueue(DefaultTableModel model)
	{
		this();
		myTableModel = model;
	}
	
	public synchronized void add(Job job)
	{
		queue.add(job);
		jobCount++;
		notifyAll();
		
		//Increase priority
		for(Job myJob: queue)
		{
			int priority = myJob.getPriority();
			priority--;
			myJob.setPriority(priority);
		}
	}
	
	public synchronized Job remove()
	{
		while(queue.isEmpty())
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return queue.poll();
	}
	
	public synchronized int getQueueCount()
	{
		return queue.size();
	}
	
	public void printItems()
	{
		//myTableModel.getDataVector().removeAllElements();
		myTableModel.getDataVector().clear();
		myTableModel.fireTableStructureChanged();

		Job[] jobArray;
		synchronized(this)
		{
			//jobArray = new Job[queue.size()];
			//jobArray = queue.toArray(jobArray);
			
			for(Job myJob: queue)
			{
				myTableModel.addRow(new Object[]{
						myJob.getJobID(), myJob.getTime(), myJob.getPriority()
				});
			}
		}
		/*
		Arrays.sort(jobArray);
		for(Job myJob: jobArray)
		{
			myTableModel.addRow(new Object[]{
					myJob.getJobID(), myJob.getTime(), myJob.getPriority()
			});
		}*/
	}
	
	public void setEnd(boolean b)
	{
		end = b;
	}
	
	public boolean getEnd()
	{
		return end;
	}
	
	public void setJobCount(int count)
	{
		jobCount = count;
	}
	
	public int getJobCount()
	{
		return jobCount;
	}
}
