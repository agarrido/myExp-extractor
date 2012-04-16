package pruebaCliente;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
import enums.Type;

public class Call {

//////////////////////////////////////////////////////////////////
	private String uriBase = "http://www.myexperiment.org";
	private ClientConfig config = new DefaultClientConfig();
	private Client client = Client.create(config);
	private WebResource service = client.resource(getBaseURI());
	
	private URI getBaseURI() {
		//el contexto del servicio
		return UriBuilder.fromUri(
				uriBase).build();
	}
/////////////////////////////////////////////////////////////////
	
	
	public void whoAmI(){
		//System.out.println(service.path("whoami.xml").accept(MediaType.TEXT_XML).get(String.class));
		System.out.println(service.path("user.xml").queryParam("id", "20629").accept(MediaType.TEXT_XML).get(String.class));
	}
	
	public String bigPage(int page){
		return (service.path("workflows.xml").queryParam("num", "50").queryParam("page", String.valueOf(page)).accept(MediaType.TEXT_XML).get(String.class));
	}
	
	public void search(String query, ArrayList<Type> types){
		if (types!=null){
			String aux="";
			for (Type t: types){
				aux=aux+t.toString()+",";
			}
			aux=aux.substring(0,aux.length()-1).toLowerCase();
			System.out.println(service.path("search.xml").queryParam("query", query).queryParam("type", aux).accept(MediaType.TEXT_XML).get(String.class));
			
		}
		else System.out.println(service.path("search.xml").queryParam("query", query).accept(MediaType.TEXT_XML).get(String.class));
	}
	
	public void resource(String id, Resources resource){
		System.out.println(service.path(resource.toString().toLowerCase()+".xml").queryParam("id", id).accept(MediaType.TEXT_XML).get(String.class));
	}
	
	public String resourceText(String id, Resources resource){
		return(service.path(resource.toString().toLowerCase()+".xml").queryParam("id", id).accept(MediaType.TEXT_XML).get(String.class));
	}
	
	public void downloadWf(String contentUri)throws IOException{
		BufferedInputStream in = new java.io.BufferedInputStream(new URL(contentUri).openStream());
		String name = contentUri.substring(contentUri.lastIndexOf("/"));
		FileOutputStream fos = new java.io.FileOutputStream("C:/wfs/"+name);
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		
		byte[] data = new byte[1024];
		int x=0;
		while((x=in.read(data,0,1024))>=0){
			bout.write(data,0,x);
		}
		bout.close();
		in.close();
	}
	
	public void downloadAllWfs(String location) {
		try {

			BufferedReader br = new BufferedReader(new FileReader(location));
			String line = "";
			String text = "";
			line = br.readLine();
			while ((line != null)){ 
				if (line.startsWith("  <content-uri>")){
					try {
						text=line.substring(15, line.lastIndexOf("</content-uri>"));
						downloadWf(text);
						System.out.println(text);
					} catch (Exception e) {}
				}
				line=br.readLine();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
	

	

}
