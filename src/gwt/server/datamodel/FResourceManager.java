package gwt.server.datamodel;

import gwt.server.SDao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class FResourceManager {

	public FResourceManager() {
	
	}
	@Id
	Long id;
	
	public List<Key<FileResource>> frlist = new ArrayList<Key<FileResource>>();
	
	public List<FileResource> getResources(){
		List<FileResource> sgl = new ArrayList<FileResource>();
		for(Key<FileResource> k : frlist){
			FileResource sg=SDao.getFResourceDao().get(k);
			sgl.add(sg);
		}
		return sgl;

	}
}
