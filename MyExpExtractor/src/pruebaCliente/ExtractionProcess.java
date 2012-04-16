package pruebaCliente;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ExtractionProcess {
	
	private ArrayList<UserWfTag> users;
	
	public ExtractionProcess(){
		
		UtilExtractionProcess u=new UtilExtractionProcess();
		//n�mero de usuarios
		int numUsers=0;
		try {
			numUsers=u.getIdLastUser();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Extracci�n de wfs por usuario en proceso-> "+numUsers+" usuarios...");
		//extraer usuarios y meter en el arrayList
		//y por cada usuario a�adir sus wf id
		users=new ArrayList<UserWfTag>();
		u.addUsers(users,numUsers);
		System.out.println("Extracci�n de wfs completa.");
		System.out.println("Tagging en proceso...");
		
		//por cada wf a�adir sus tags (y si se puede, una tabla hash que diga qu� usuarios usan qu� tags)
		u.insertTags(users);
		System.out.println("Tagging completado");
	}

}
