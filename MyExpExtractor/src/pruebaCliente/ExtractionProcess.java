package pruebaCliente;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ExtractionProcess {
	
	private ArrayList<UserWfTag> users;
	
	public ExtractionProcess(){
		
		UtilExtractionProcess u=new UtilExtractionProcess();
		//número de usuarios
		int numUsers=0;
		try {
			numUsers=u.getIdLastUser();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Extracción de wfs por usuario en proceso-> "+numUsers+" usuarios...");
		//extraer usuarios y meter en el arrayList
		//y por cada usuario añadir sus wf id
		users=new ArrayList<UserWfTag>();
		u.addUsers(users,numUsers);
		System.out.println("Extracción de wfs completa.");
		System.out.println("Tagging en proceso...");
		
		//por cada wf añadir sus tags (y si se puede, una tabla hash que diga qué usuarios usan qué tags)
		u.insertTags(users);
		System.out.println("Tagging completado");
	}

}
