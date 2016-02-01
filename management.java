import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import util.LinkOperation;
import util.recordLinks;
import util.resultLinks;
import crud.fileOperation;
import data.Guitar;
import data.IDataObject;
import data.Record;


public class management {
	//This method reads in the 
	public boolean sale(Record record) {
		fileOperation fileOp = new fileOperation();
		fileOp.setFileName("record.guitar");
		fileOp.setRwMode("rw");
		fileOp.openFile();
		
		long pos = fileOp.search(record.getName(), 1, 0);
		if (pos <= 0) 
		{
		    //if there is no corresponding data in that file
			System.out.println("No Stock Left!!");
			fileOp.closeFile();
			return false;
		} else {
			//pos = fileOp.search(record.getName(), 1, 0);
			String[] tmp = new String[5];
			Guitar guitar = new Guitar();
			
			fileOp.setPos(pos);
			resultLinks res = fileOp.search(record.getName(), 1);
			for (int i = 0; i < 5 && res.getNext() != null; i++) {
				res = res.getNext();
				tmp[i] = res.getValue();
			}
			guitar.fromString(tmp);
			int p_profit = guitar.getProfit() + record.getNum()*(record.getPrice()-guitar.getPrice());
			guitar.setProfit(p_profit);
			guitar.setSalesVolumne(guitar.getSalesVolumne() + record.getNum());
			if (guitar.getStock() - record.getNum() >= 0)
				guitar.setStock(guitar.getStock() - record.getNum());
			else {
				System.out.println("No Stock Left!!");
				fileOp.closeFile();
				return false;
			}
			fileOp.modifyOrderFile(guitar, pos, 4, false);
			fileOp.closeFile();
			
			//insert a sale record file
			fileOp.setFileName("out.guitar");
			fileOp.setRwMode("rw");
			fileOp.openFile();
			if(!fileOp.insert(record, fileOp.getLength())){
				fileOp.closeFile();
				return false;
			}
			fileOp.closeFile();
			return true;
		}
	}
	//This method records information about stock
	public void in(Record record){
		
		fileOperation fileOp = new fileOperation();
		fileOp.setFileName("in.guitar");
		fileOp.setRwMode("rw");
		fileOp.openFile();
		fileOp.insert(record, fileOp.getLength());
		fileOp.closeFile();
		//After loading from the file, we need to read data from another file called record.guitar
		fileOp.setFileName("record.guitar");
		fileOp.openFile();
		
		long pos = fileOp.search(record.getName(), 1, 0);
		if(pos <=0){
			Guitar guitar = new Guitar();
			guitar.setName(record.getName());
			guitar.setPrice(record.getPrice());
			guitar.setSalesVolumne(0);
			guitar.setStock(record.getNum());
			guitar.setProfit(0);
			fileOp.insertSortFile(guitar, 4, false);
			fileOp.closeFile();
		}
		else{
			String[] tmp = new String[5];
			fileOp.setPos(pos);
			resultLinks res = fileOp.search(record.getName(), 1);
			for(int i =0;i< 5&&res.getNext()!=null;i++){
				res = res.getNext();
				tmp[i] = res.getValue();
			}
			Guitar guitar = new Guitar();
			guitar.fromString(tmp);
			guitar.setStock(guitar.getStock()+record.getNum());
			guitar.setPrice(record.getPrice());
			fileOp.modifyOrderFile(guitar, pos, 4, false);
			fileOp.closeFile();
		}
	}
	
	public static void displayAll(String filename){
		fileOperation fileOp = new fileOperation();
		fileOp.setFileName(filename);
		fileOp.setRwMode("r");
		fileOp.openFile();
		resultLinks res = fileOp.readAll();
		fileOp.closeFile();
		LinkOperation linkOp = new LinkOperation();
		recordLinks recLinks = null;
		if(filename == "in.guitar"|| filename == "out.guitar")
			recLinks = linkOp.generateLink(res, 1);
		else 
			recLinks = linkOp.generateLink(res, 2);
		linkOp.display(recLinks);
	}
	
	
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//set time format
		return df.format(new Date());// get system time
	}
	
}
