import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class XMLParser {
	// xml parsing
		ArrayList<InitialServer> defaultedServers;
	
		public XMLParser() {
			defaultedServers = parseServers();
		}
		
		ArrayList<InitialServer> parseServers(){
			try {
				File inputFile = new File("./system.xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(inputFile);
				doc.getDocumentElement().normalize();
				ArrayList<InitialServer> servers = new ArrayList<InitialServer>();
				Element node = (Element) doc.getElementsByTagName("servers").item(0);
				for (int i = 0; i < node.getElementsByTagName("server").getLength(); i++) {
					NamedNodeMap m = node.getElementsByTagName("server").item(i).getAttributes();
					InitialServer temp = new InitialServer();
					temp.type = m.getNamedItem("type").getNodeValue();
					temp.cpuCore = Integer.parseInt(m.getNamedItem("coreCount").getNodeValue());
					temp.memory = Integer.parseInt(m.getNamedItem("memory").getNodeValue());
					temp.disk = Integer.parseInt(m.getNamedItem("disk").getNodeValue());
					servers.add(temp);
				}
				
				return servers;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		ArrayList<InitialServer> getServers(){
			//print();
			return defaultedServers;
		}
		
		void print() {
			for(int i=0;i<defaultedServers.size();i++) {
				defaultedServers.get(i).print();
			}
		}

}
