import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class WorstFit implements Algorithm {


	int worstFit = Integer.MIN_VALUE;
	int altFit = Integer.MIN_VALUE;
	int Fitnessval = 0;
	int serverCore = 0;

	SortedMap<Integer, ServerInfo> iniList = new TreeMap<>();

	ServerInfo AltServer;
	ServerInfo wstServer;
	ServerInfo IniServer;

	public WorstFit(ArrayList<InitialServer> defaultedServers) {
		worstFit = Integer.MIN_VALUE;
		altFit = Integer.MIN_VALUE;
		AltServer = null;
		wstServer = null;
	}

	public boolean isFitted(Job job, ServerInfo server) {
		return (job.cpuCore<=server.cpuCore) && (job.disk<=server.diskSpace) &&
				(job.memory<=server.memory);
	}

	@Override
	public void getServer(ServerInfo server, Job job) {
		// TODO Auto-generated method thingy
		//initList.put(server.cpuCore,server); //add the fucking initail value
		if(isFitted(job,server)) {
		    iniList.put(server.cpuCore,server);
		}
		if(server.state!=4 &&isFitted(job,server)) {
			int Fitnessval = server.cpuCore - job.cpuCore;
			if(Fitnessval>worstFit && (server.state==3 || server.state==2)) {
				worstFit = Fitnessval;
				wstServer = server;
			}
			else if(Fitnessval>altFit&&(server.state==0||(server.availableTime==-1 && server.state==1))) {
				altFit = Fitnessval;
				AltServer = server;  //0,1    
			}
		}  
		return;
		
		
		
//		if(isFitted(job,server)) {
//			//do thing with the initial
//			if(server.state!=4&&isFitted(job,server)) {
//				                  ///do things with the worst fit
//			}
//			else if(Fitnessval>altFit) {
//				                  /// do things with the alt
//			}
//		}
	}

	@Override
	public ServerInfo bestServer() {
		if((worstFit!=Integer.MIN_VALUE)) {
			return wstServer;
		}
		else if((altFit!=Integer.MIN_VALUE)) {
			return AltServer;
		}
		return iniList.get(iniList.lastKey());
	}
}
