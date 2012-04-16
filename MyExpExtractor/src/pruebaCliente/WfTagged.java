package pruebaCliente;

import java.util.ArrayList;

public class WfTagged {
	
	private ArrayList<Tag> listTags=null;
	private int id;
	
	public WfTagged(int id){
		this.id=id;
	}
	
	public void addTag(Tag tag){
		if (listTags==null) listTags=new ArrayList<Tag>();
		listTags.add(tag);	
	}

	public int getId() {
		return id;
	}

}
