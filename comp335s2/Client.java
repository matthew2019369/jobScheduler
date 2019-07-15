import java.net.*;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.util.ArrayList;

public class Client {

	private Socket socket = null;
	private DataOutputStream out = null;
	private BufferedReader in = null;

	public Client(String address, int port) throws Exception {
		socket = new Socket(address, port);
		System.out.println("Connected");

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

	}

	// send message to the server
	private void say(String str) throws Exception {
		String msg = str;
		byte[] b = msg.getBytes();
		out.write(b, 0, b.length);
		out.flush();
	}

	// receive message from server
	public String getFromServer() throws Exception {
		StringBuilder result = new StringBuilder();
		while (result.length() < 1) {
			while (in.ready()) {
				result.append((char) in.read());
			}
		}

		//System.out.println(result.toString());
		return result.toString();
	}

	// function to authenticate the user
	public void auth(String userName) throws Exception {
		String msg = "AUTH " + userName;
		say(msg);
	}

	// function to signal the server
	public void ready() throws Exception {
		String msg = "REDY";
		say(msg);
	}

	// function to schedule
	public void schedule(int jobID, String serverSize, int server) throws Exception {
		String msg = "SCHD " + Integer.toString(jobID) + " " + serverSize + " " + Integer.toString(server);
		say(msg);
	}

	// function say hello
	public void hello() throws Exception {
		say("HELO");
	}

	public void okay() throws Exception {
		say("OK");
	}

	public void resc() throws Exception {
		String msg = "RESC All";
		say(msg);
	}

	public void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}



	//to assign algorithm based on the argument inserted
	public static Algorithm getAlgorithm(String str, ArrayList<InitialServer> defaultedServers) {
		if(str.equals("ff")) {
			return new FirstFit(defaultedServers);
		} else if (str.equals("wf")) {
			return new WorstFit(defaultedServers);
		} else if (str.equals("bf")){
			return new BestFit(defaultedServers);
		} else {
			return new AllToLargest(defaultedServers);
		}
}


	public static void main(String[] args) {
		try {

			Client client = new Client("127.0.0.1", 8096);

			//default algorithm is FirstFit
			String algorithm = "lf";
			//this is used in merged application only
			for(int i=0;i<args.length-1;i++) {
			 	if(args[i].equals("-a")) {
			 		if(args[i+1].equals("bf")) {
			 			algorithm="bf";
			 			break;
			 		} else if (args[i+1].equals("wf")) {
			 			algorithm="wf";
			 			break;
			 		} else if (args[i+1].equals("ff")) {
						algorithm="ff";
						break;
					}
			 	}
			}


			client.hello();
			client.getFromServer();

			client.auth("comp335");
			client.getFromServer();

			client.ready();
			String line = client.getFromServer();

			XMLParser xmlParser = new XMLParser();

			while (!line.equals("NONE")) {

				String[] temp = line.split(" ");

				Job job = new Job(temp);
				client.resc();

				//define and initiate an object of "Algorithm"
				Algorithm algor = getAlgorithm(algorithm,xmlParser.getServers());

				if (client.getFromServer().equals("DATA")) {


					String t2 = "";
					client.okay();
					while (true) {

						t2=client.getFromServer();

						if(t2.equals(".")) {

							break;
						}
						String[] tmp = t2.split(" ");

						ServerInfo newServer = new ServerInfo(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);
						algor.getServer(newServer, job);
						client.okay();
					}

				}
				//System.out.println("Schedule to: "+job.jobID+" " +algor.bestServer().type +" "+algor.bestServer().id);
				client.schedule(job.jobID, algor.bestServer().type, algor.bestServer().id);
				client.getFromServer();
				client.ready();
				line = client.getFromServer();
			}
			client.say("QUIT");
			client.getFromServer();
			client.close();

		} catch (ConnectException ce) {
			System.out.println("Connection to socket is not allowed: server not available");
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
