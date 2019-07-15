
//interface for all three algorithms 
public interface Algorithm {
	
	//signature to pass the server to individual algorithm to determine whether it is the best server
	public void getServer(ServerInfo server, Job job);
	
	//signature to retrieve the ideal server based on individual algorithm
	public ServerInfo bestServer();
}
