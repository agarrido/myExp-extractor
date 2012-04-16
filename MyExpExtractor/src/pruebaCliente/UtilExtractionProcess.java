package pruebaCliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import enums.Resources;

public class UtilExtractionProcess {
	
	private String uriBase = "http://www.myexperiment.org";
	private ClientConfig config = new DefaultClientConfig();
	private Client client = Client.create(config);
	private WebResource service = client.resource(getBaseURI());
	
	private URI getBaseURI() {
		//el contexto del servicio
		return UriBuilder.fromUri(
				uriBase).build();
	}
//////////////////////////////////////////////////////////////////
	
	public int getIdLastUser() throws MalformedURLException, IOException{
		String contentUri=(service.path("users.xml").queryParam("sort", "id").queryParam("order", "reverse").accept(MediaType.TEXT_XML).get(String.class));
			//BufferedInputStream in = new java.io.BufferedInputStream(new URL(contentUri).openStream());
			int begIndex = contentUri.indexOf("http://www.myexperiment.org/users/");
			int endIndex = contentUri.indexOf(" uri=\"http://www.myexperiment.org/user.xml?id=");
			String num = contentUri.substring(begIndex+34, endIndex-1);
			
			return Integer.parseInt(num);
	}

	public void addUsers(ArrayList<UserWfTag> users, int numUsers) {
		String aux=null;
			for (int i=1; i<numUsers; i++){
				try {
					aux=(service.path(Resources.USER.toString().toLowerCase()+".xml").queryParam("id", String.valueOf(i)).queryParam("elements", "workflows").accept(MediaType.TEXT_XML).get(String.class));
					extractUserAndWfs(i,aux,users);
					if (i%100==0)System.out.println("user:"+i);
				} catch (Exception e) {
				}
			}

	}
	
	
	private void extractUserAndWfs(int id, String content, ArrayList<UserWfTag> users) {
		UserWfTag u=new UserWfTag(id);
		BufferedReader br=new BufferedReader(new StringReader(content));
		String line = "";
		String text = "";
		try {
			line = br.readLine();
			while ((line != null)){ 
				if (line.startsWith("    <workflow uri")){
					try {
						int ini=line.indexOf(".xml?id=")+8;
						int end=line.indexOf(" resource=")-1;
						text=line.substring(ini,end);
						u.add(Integer.parseInt(text));
					} catch (Exception e) {}
			}
			line=br.readLine();
		}
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (u.hasWfs())
			users.add(u);
	}

	public void insertTags(ArrayList<UserWfTag> users) {
		int id=0;
		for (UserWfTag uwt: users){
			for (WfTagged wt: uwt.getWfs()){
				id=wt.getId();
				extractTags(uwt.getId(),wt, service.path(Resources.WORKFLOW.toString().toLowerCase()+".xml").queryParam("id", String.valueOf(id)).queryParam("elements", "tags").accept(MediaType.TEXT_XML).get(String.class));
			}
		}
		
	}
	
	private void extractTags(int idUser, WfTagged wt, String content) {
		BufferedReader br=new BufferedReader(new StringReader(content));
		String line = "";
		String text = "";
		int tagId=0;
		try {
			line = br.readLine();
			while ((line != null)){ 
				if (line.startsWith("    <tag uri=")){
					try {
						int ini=line.indexOf("\">")+2;
						int end=line.indexOf("</tag>");
						text=line.substring(ini,end);
						ini=line.indexOf(".xml?id=")+8;
						end=line.indexOf(" resource=")-1;
						tagId=Integer.parseInt(line.substring(ini,end));
						wt.addTag(new Tag(tagId, text));
						if (idUser%50==0)System.out.println("Tagging user:"+idUser);
					} catch (Exception e) {}
			}
			line=br.readLine();
		}
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}	
	

}
