package pruebaCliente;

import java.util.ArrayList;

public class UserWfTag {
	
	private ArrayList<WfTagged> wfList=null;
	private int id;
	
	public UserWfTag(int id){
		this.id=id;
	}
	
	public int getId(){
		return id;
	}
	
	public void add(int wfid){
		if (wfList==null) wfList=new ArrayList<WfTagged>();
		
		wfList.add(new WfTagged(wfid));
	}
	
	public void addWfTag(int wfId, int tagId, String tag){
		if (wfList==null) wfList=new ArrayList<WfTagged>();
		
		int i=0;
		boolean found=false;
		while (i<wfList.size() && !found){
			if (wfList.get(i).getId()==wfId){
				wfList.get(i).addTag(new Tag(tagId, tag));
				found=true;
			}
		}
		
		if (!found){
			WfTagged wft= new WfTagged(wfId);
			wft.addTag(new Tag(tagId,tag));
			wfList.add(wft);
		}
	}

	public boolean hasWfs() {
		if (wfList!=null && wfList.size()>0)
			return true;
		else
			return false;
	}

	public ArrayList<WfTagged> getWfs(){
		return wfList;
	}
	

}
