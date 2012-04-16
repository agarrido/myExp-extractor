package pruebaCliente;


import java.net.URI;

import javax.ws.rs.core.UriBuilder;



public class Cliente {
	
	public static void main(String[] args) {
		/*ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());*/
		
		/*// Get XML
		System.out.println(service.path("search.xml").queryParam("query", "bioaid").accept(
				MediaType.TEXT_XML).get(String.class));*/
		
		Call c=new Call();
		//c.whoAmI();
		
		/*ArrayList<Type> t=new ArrayList<Type>();
		t.add(Type.WORKFLOW); t.add(Type.PACK);
		c.search("bioaid", t);*/
		
		/*ArrayList<Integer> exist=new ArrayList<Integer>();
		boolean content=true;
		String wf;
		int page=1;
		while (content){
			wf=c.bigPage(page));
			wf.s
			page++;
		}*/
		
		/*try {
			c.downloadWf("http://www.myexperiment.org/workflows/14/download/pathways_and_gene_annotations_for_qtl_phenotype_25510.xml");
		} catch (IOException e) {
		}*/
		
		String a= "<banana>rama";
		if (a.startsWith("<banana>"))
			a="platano";
		
		c.downloadAllWfs("C:/workflows2.txt");
		
		
		/*PrintStream out = null;
		String aux;
			
		try {
			out = new PrintStream(new FileOutputStream("C:/workflows.txt"));
			for (int i=1; i<2633; i++){
				try {
					aux=c.resourceText(String.valueOf(i),Resources.WORKFLOW);
					out.println(aux);
					System.out.println(i);
				} catch (Exception e) {
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		
		



	}

	private static URI getBaseURI() {
		//el contexto del servicio
		return UriBuilder.fromUri(
				"http://www.myexperiment.org").build();
	}

}