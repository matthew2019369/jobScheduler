public class ServerInfo {
	/*
	 * this is an object to save the detail of a server sent from ds-server
	 */
	String type;
	int id;
	int state;
	int availableTime;
	int cpuCore;
	int memory;
	int diskSpace;

	// constructor
	public ServerInfo(String type, String id, String state, String availableTime, String cpuCore, String memory,
			String diskSpace) {
		this.type = type;
		this.id = Integer.parseInt(id);
		this.state = Integer.parseInt(state);
		this.availableTime = Integer.parseInt(availableTime);
		this.cpuCore = Integer.parseInt(cpuCore);
		this.memory = Integer.parseInt(memory);
		this.diskSpace = Integer.parseInt(diskSpace);
	}
	//used for debugging
	public void print() {
		System.out.println("This server has type: " + type + " id: " + id + " state: " + state + " availableTime: "
				+ availableTime + " cpuCore: " + cpuCore + " memory: " + memory + " diskspace: " + diskSpace);
	}
}