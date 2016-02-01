package util;

public class resultLinks{
	String value;
	resultLinks next;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public resultLinks getNext() {
		return next;
	}
	public void setNext(resultLinks next) {
		this.next = next;
	}
	
	public void display(resultLinks res){
		while(res != null){
			System.out.println(res.getValue());
			res = res.getNext();
		}
	}
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(value);
	}
	
}
