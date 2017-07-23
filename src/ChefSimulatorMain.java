import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


public class ChefSimulatorMain {

	private JFrame frmRestaurantChefCooking;
	private JLabel label1, label2, label3, timeLabel, resultLabel, queueLabel;
	private JButton btn1;
	private JobQueue myQueue;
	private boolean stopButton = false;
	private ProcessJobThread task1, task2, task3;
	private JTable table;
	private DefaultTableModel myModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChefSimulatorMain window = new ChefSimulatorMain();
					window.frmRestaurantChefCooking.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChefSimulatorMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRestaurantChefCooking = new JFrame();
		frmRestaurantChefCooking.setTitle("Restaurant Chef Cooking Simulator");
		frmRestaurantChefCooking.setBounds(100, 100, 561, 530);
		frmRestaurantChefCooking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRestaurantChefCooking.getContentPane().setLayout(null);
		
		btn1 = new JButton("Click me to start!");
		btn1.setBounds(191, 457, 187, 23);
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!stopButton)
				{
					startJob();
					resultLabel.setText("Results:");
					btn1.setText("Click me to end!");
					stopButton = true;
					startTime(6);
				}
				else
				{
					myQueue.setEnd(true);
					stopButton = false;
					btn1.setText("Click me to start!");
				}
			}
		});

		frmRestaurantChefCooking.getContentPane().add(btn1);
		
		label1 = new JLabel("<html>Chef "+ 1 + "<br>Waiting for job</html>");
		label1.setVerticalAlignment(SwingConstants.TOP);
		label1.setBounds(10, 38, 120, 70);
		frmRestaurantChefCooking.getContentPane().add(label1);
		
		label2 = new JLabel("<html>Chef "+ 2 + "<br>Waiting for job</html>");
		label2.setVerticalAlignment(SwingConstants.TOP);
		label2.setBounds(140, 38, 120, 70);
		frmRestaurantChefCooking.getContentPane().add(label2);
		
		label3 = new JLabel("<html>Chef "+ 3 + "<br>Waiting for job</html>");
		label3.setVerticalAlignment(SwingConstants.TOP);
		label3.setBounds(270, 38, 120, 70);
		frmRestaurantChefCooking.getContentPane().add(label3);
		
		resultLabel = new JLabel("Results:");
		resultLabel.setVerticalAlignment(SwingConstants.TOP);
		resultLabel.setBounds(10, 275, 487, 171);
		frmRestaurantChefCooking.getContentPane().add(resultLabel);
		
		timeLabel = new JLabel("Time: ");
		timeLabel.setBounds(10, 11, 151, 14);
		frmRestaurantChefCooking.getContentPane().add(timeLabel);
		
		queueLabel = new JLabel("Queue: ");
		queueLabel.setBounds(10, 119, 287, 14);
		frmRestaurantChefCooking.getContentPane().add(queueLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 144, 487, 120);
		frmRestaurantChefCooking.getContentPane().add(scrollPane);
		
		
		myModel = new DefaultTableModel(
				new Object[][]{},
				new String[]{"Job ID", "Job Length", "Priority - the lower the better"});
		
		table = new JTable(myModel);
		scrollPane.setViewportView(table);		
	}
	
	private void startJob()
	{
		myQueue = new JobQueue(myModel);
		
		Thread generateJobThread = new Thread(new GenerateJobThread(myQueue));
		
		task1 = new ProcessJobThread(myQueue, 1, label1);
		task2 = new ProcessJobThread(myQueue, 2, label2);
		task3 = new ProcessJobThread(myQueue, 3, label3);
		
		Thread processThread1 = new Thread(task1);
		Thread processThread2 = new Thread(task2);
		Thread processThread3 = new Thread(task3);
		
		generateJobThread.start();
		processThread1.start();
		processThread2.start();
		processThread3.start();
	}
	
	private void startTime(int minute)
	{	
		Thread timeThread = new Thread(new Runnable() {
			public void run() {
				int time = minute * 60;
				while(time > 0 && !myQueue.getEnd())
				{
					time--;
					timeLabel.setText("Time: " + time);
					queueLabel.setText("Queue: " + myQueue.getQueueCount());
					
					//print table
					myQueue.printItems();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				myQueue.setEnd(true);
				btn1.setText("Click me to start!");
				stopButton = false;
				
				int totalJob = task1.getTotalCount() + task2.getTotalCount() + task3.getTotalCount();
				int attempt1 = task1.getCount(1)+task2.getCount(1)+task3.getCount(1);
				int attempt2 = task1.getCount(2)+task2.getCount(2)+task3.getCount(2);
				int attempt3 = task1.getCount(3)+task2.getCount(3)+task3.getCount(3);
				
				resultLabel.setText(String.format("<html>Results:<br>Job processed:<br>"+
						"Chef 1: %d jobs, Chef 2: %d jobs, Chef 3: %d jobs<br><br>"+
						"Total jobs processed: %d jobs<br><br>"+
						"Average jobs arrival rate: %.2f per minute<br><br>"+
						"Job attempts:<br>1 attempt: %d jobs, 2 attempts: %d jobs, 3 attempts: %d jobs</html>",
						task1.getTotalCount(), task2.getTotalCount(), task3.getTotalCount(),
						totalJob, myQueue.getJobCount()/(minute-time/60.0), attempt1, attempt2, attempt3));
			}
		});
		timeThread.start();
	}
}
