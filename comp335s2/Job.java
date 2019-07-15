
public class Job {
	// object for job
	int submitTime;
	int jobID;
	int estimatedRunTime;
	int cpuCore;
	int memory;
	int disk;

	public Job(String[] arr) {
		
		this.submitTime = Integer.parseInt(arr[1]);
		this.jobID = Integer.parseInt(arr[2]);
		this.estimatedRunTime = Integer.parseInt(arr[3]);
		this.cpuCore = Integer.parseInt(arr[4]);
		this.memory = Integer.parseInt(arr[5]);
		this.disk = Integer.parseInt(arr[6]);
	}

}