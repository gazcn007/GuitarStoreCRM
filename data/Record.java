package data;

public class Record extends IDataObject{

	String name;
	String date;
	int num;
	int price;
	String[] info;
	
	public Record(){}
	
	public Record(String name, String date, int num, int price){
		this.name = name;
		this.date = date;
		this.num = num;
		this.price = price;
	}
	
	public void fromString(String[] str) {
		// TODO Auto-generated method stub
		this.name = str[0];
		this.date = str[1];
		this.num = Integer.parseInt(str[2]);
		this.price = Integer.parseInt(str[3]);
	}

	public String[] packetInfo() {
		// TODO Auto-generated method stub
		info = new String[4];
		info[0] = this.name;
		info[1] = this.date;
		info[2] = this.num+"";
		info[3] = this.price+"";
		return info;
	}

	public void display() {
		// TODO Auto-generated method stub
		System.out.println("name: "+name+"\ndate: "+date+"\nnum: "+num+"\nprice: "+price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
