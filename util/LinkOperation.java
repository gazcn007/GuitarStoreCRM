package util;

import data.Guitar;
import data.IDataObject;
import data.Record;

public class LinkOperation {
	public recordLinks generateLink(resultLinks res, int type){
		if(res == null)
			return null;
		else if(res.getNext() == null)
			return null;
		int columnNum = -1;
		recordLinks record = new recordLinks();
		recordLinks last = record;
		recordLinks tmp = new recordLinks();
		columnNum = Integer.parseInt(res.getValue());
		res = res.getNext();
		while(true){
			String[] str = new String[columnNum];
			IDataObject value = null;
			if(type == 1)
				value = new Record();
			else if(type == 2)
				value = new Guitar();
			else {
				return record;
			}
					int i=0;
					for(i=0;i<columnNum;i++){
						str[i] = res.getValue();
						res = res.getNext();
						if(res == null)
							break;
					}
					if(i == columnNum|| i == columnNum-1)
					{
						value.fromString(str);
						tmp = new recordLinks();
						tmp.setValue(value);
						tmp.setNext(null);
						last.setNext(tmp);
						last = tmp;
					}
					if(i < columnNum)
					{
						return record;
					}
			
		}
	}
	
	
	public ILinks rank(ILinks list, int column, boolean isAsc){
		if(list == null){
			System.out.println("file is empty!");
			return null;
		}
		else if(list.getNext() == null){
			System.out.println("file is empty!");
			return null;
		}
		ILinks res = new recordLinks();
		res.setNext(null);
		ILinks tmpLinks = list.getNext();
		while(tmpLinks!=null){
			tmpLinks.getValue().packetInfo();
			tmpLinks = tmpLinks.getNext();
		}
		ILinks head;
		list = list.getNext();
		while (list != null) {
			head = res;

			while (head.getNext() != null) {
				int tmp = Integer.parseInt(head.getNext().getValue().getData(
						column));
				if (isAsc) {
					if (tmp > Integer.parseInt(list.getValue().getData(column))) {
						tmpLinks = new recordLinks();
						tmpLinks.setValue(list.getValue());
						tmpLinks.setNext(head.getNext());
						head.setNext(tmpLinks);
						break;
					}
				} else {
					if (tmp < Integer.parseInt(list.getValue().getData(column))) {
						tmpLinks = new recordLinks();
						tmpLinks.setValue(list.getValue());
						tmpLinks.setNext(head.getNext());
						head.setNext(tmpLinks);
						break;
					}
				}
				head = head.getNext();
			}
			if (head.getNext() == null) {
				tmpLinks = new recordLinks();
				tmpLinks.setValue(list.getValue());
				tmpLinks.setNext(null);
				head.setNext(tmpLinks);
			}

			list = list.getNext();
		}
		return res;
	}
	
	public void display(ILinks link){
		if(link==null)
			{
				System.out.println("There is no record!");
				return;
			}
		link = link.getNext();
		while(link != null){
			System.out.println("*****************");
			link.display();
			System.out.println("*****************");
			link = link.getNext();
		}
	}
}
