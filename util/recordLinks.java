package util;

import data.IDataObject;

public class recordLinks extends ILinks{
	IDataObject value;
	ILinks next;
	public IDataObject getValue() {
		return value;
	}
	public void setValue(IDataObject value) {
		this.value = value;
	}
	public ILinks getNext() {
		return next;
	}
	public void setNext(ILinks next) {
		this.next = next;
	}
	public void display() {
		// TODO Auto-generated method stub
		value.display();
	}
	
	/*public i rank(boolean isAsc, int column){
		ILinks res = new recordLinks();
		res.setNext(null);
		ILinks list = this.getNext();
		while()
		return res;
	}*/
}
