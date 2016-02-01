package data;

public class Guitar extends IDataObject{
 
	String name;
	int stock;
	int price;
	int salesVolumne;
	int profit;
	String[] info;
	
	public Guitar(){}
	
	public Guitar(String name, int stock, int price, int salesVolumne, int profit){
		this.name = name;
		this.stock = stock;
		this.price = price;
		this.salesVolumne = salesVolumne;
		this.profit = profit;
	}
	
	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getSalesVolumne() {
		return salesVolumne;
	}
	public void setSalesVolumne(int salesVolumne) {
		this.salesVolumne = salesVolumne;
	}
	
	public String[] packetInfo(){
		info = new String[5];
		info[0] = name;
		info[1] = stock + "";
		info[2] = price + "";
		info[3] = salesVolumne + "";
		info[4] = profit + "";
		return info;
	}
	
	public void fromString(String[] str){
		this.name = str[0];
		this.stock = Integer.parseInt(str[1]);
		this.price = Integer.parseInt(str[2]);
		this.salesVolumne = Integer.parseInt(str[3]);
		this.profit = Integer.parseInt(str[4]);
	}
	
	public String getData(int index) {
		// TODO Auto-generated method stub
		if(info == null)
			return null;
		if(index <= info.length)
			return info[index-1];
		else 
			return null;
	}
	public void display(){
		System.out.println("name: "+name+"\nstock: "+stock+"\nprice: "+price+"\nsalesVolumne: "+salesVolumne+"\nprofit: "+profit);
	}
	
}
