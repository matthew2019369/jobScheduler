import java.util.ArrayList;

public class AllToLargest implements Algorithm{
	String largestType="";
	ArrayList<InitialServer> defaultedServers;
	public AllToLargest(ArrayList<InitialServer> defaultedServers) {
		this.defaultedServers = defaultedServers;
		getLargest();
	}
	
	void getLargest(){
		if(defaultedServers!=null&&defaultedServers.size()>0){
			InitialServer max = defaultedServers.get(0);
			for(int i =1;i<defaultedServers.size();i++){
				if(defaultedServers.get(i).cpuCore>max.cpuCore){
					max = defaultedServers.get(i);
				}
			}
			largestType = max.type;
			//System.out.println("The largest server i found is : "+largestType);
		}
	}
	@Override
	public void getServer(ServerInfo server, Job job) {
		return;
		
	}

	@Override
	public ServerInfo bestServer() {
		// TODO Auto-generated method stub
		return new ServerInfo(largestType,"0","0","0","0","0","0");
	}

}
