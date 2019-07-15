import java.util.ArrayList;

public class BestFit implements Algorithm{
	//bestFit algorithm
	int bestFit;
	int minAvail;
	ServerInfo server;
	

	int initialMinAvail;
	ServerInfo initialServer;
	
	ArrayList<InitialServer> defaultedServers;
	
	//constructor
	public BestFit (ArrayList<InitialServer> defaultedServers) {
		bestFit = Integer.MAX_VALUE;
		minAvail = Integer.MAX_VALUE;
		server = null;
		

		initialMinAvail = Integer.MAX_VALUE;
		initialServer = null;
		
		this.defaultedServers = defaultedServers;
	}
	
	@Override
	public void getServer(ServerInfo newServer, Job job) {
		if(newServer.state!=4 &&isFitted(job, newServer)) {
			int fitnessVal = newServer.cpuCore-job.cpuCore;
			if(fitnessVal<bestFit||(fitnessVal==bestFit&&newServer.availableTime<minAvail)) {
				bestFit= fitnessVal;
				minAvail = newServer.availableTime;
				server= newServer;
				
			}
		} else if(initialServer==null&&(newServer.state==1||newServer.state==3)) {
			if(isInitiallyFitted(job, newServer)) {
				if(newServer.availableTime<initialMinAvail) {
					initialMinAvail = newServer.availableTime;
					initialServer= newServer;
				}
			}
			
		}
	}
	
	//a boolean to decide whether the job is capable to run on a server
	public boolean isFitted(Job job, ServerInfo server) {
		return (job.cpuCore<=server.cpuCore) && (job.disk<=server.diskSpace) &&
				(job.memory<=server.memory);
	}
	
	public boolean isInitiallyFitted(Job job, ServerInfo server) {
		String type = server.type;
		for(int i=0;i<defaultedServers.size();i++) {
			if(type.equals(defaultedServers.get(i).type)) {
				return (job.cpuCore<=defaultedServers.get(i).cpuCore) && (job.disk<=defaultedServers.get(i).disk) &&
						(job.memory<=defaultedServers.get(i).memory);
			}
		}
		return false;
	}
	//return the ideal Server using bestFit algorithm 
	public ServerInfo bestServer() {

		if(server!=null) return server;
		return initialServer;
	}

}
