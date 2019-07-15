import java.util.ArrayList;

public class FirstFit implements Algorithm{
	ServerInfo firstServer;
	boolean isFFFound=false;
	
	ServerInfo initialServer;
	ArrayList<InitialServer> defaultedServers;
	public FirstFit(ArrayList<InitialServer> defaultedServers) {
		firstServer = null;
		this.defaultedServers=defaultedServers;
		initialServer = null;
	}
	@Override
	public void getServer(ServerInfo server, Job job) {
		if(server==null||server.state==4) return;
		// TODO Auto-generated method stub
		if(!isFFFound && isFitted(job, server)) {
			firstServer=server;
			isFFFound=true;
			
		}
		if((server.state==1||server.state==3)) {
			if(isInitiallyFitted(job, server)) {
				initialServer=server;
				
			}
		}
	}

	//a boolean to decide whether the job is capable to run on a server
	public boolean isFitted(Job job, ServerInfo server) {
		return (job.cpuCore <= server.cpuCore) && (job.disk <= server.diskSpace) && (job.memory <= server.memory);
	}

	public boolean isInitiallyFitted(Job job, ServerInfo server) {
		String type = server.type;
		for (int i = 0; i < defaultedServers.size(); i++) {
			if (type.equals(defaultedServers.get(i).type)) {
				return (job.cpuCore <= defaultedServers.get(i).cpuCore) && (job.disk <= defaultedServers.get(i).disk)
						&& (job.memory <= defaultedServers.get(i).memory);
			}
		}
		return false;
	}

	@Override
	public ServerInfo bestServer() {
		if(firstServer!=null) {
			return firstServer;
		} else {
			return initialServer;
		}
	}
}
