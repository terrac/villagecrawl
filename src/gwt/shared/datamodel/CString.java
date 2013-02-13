package gwt.shared.datamodel;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Too afraid to just try and use object and cast
 * @author terra
 *
 */
public class CString implements IClientObject,IsSerializable{
	public String value;

	public CString(String value) {
		super();
		this.value = value;
	}
	public CString() {
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
}
