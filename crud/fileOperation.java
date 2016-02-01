package crud;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.sun.org.apache.bcel.internal.generic.RETURN;


import data.IDataObject;
import util.resultLinks;

public class fileOperation {
	private String fileName;
	private RandomAccessFile file;
	private static boolean isOpen = false;
	private String rwMode;
	static String endOfFile = "end of file";
	
	
	public boolean openFile()
	{
		try {
			if(!isOpen){
				file = new RandomAccessFile(fileName,rwMode);
				isOpen = true;
			}
			else {
				System.out.println("file has been inited!");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	public boolean closeFile()
	{
		try {
			file.close();
			isOpen = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//Search for key words in the files, and return the results in a form of linked list
	public resultLinks search(String obj, int column){
		resultLinks res = new resultLinks();
		res.setNext(null);
		resultLinks tmp,last;
		last = res;
		int columnNum = -1;
		if(file == null){
			return null;
		}
		try {
			if(file.length() == 0)
				return res;
			//record the position of a record in a file
			long posBegin ;
			file.seek(0);
			//read the string length of each record in the beginning of a file
			columnNum = file.readInt();
			res.setValue(columnNum+"");
			file.readLine();
			//save the position of each record in the file, so that it can get access to the position
			posBegin = file.getFilePointer();
			while(true){
				posBegin = search(obj, column, file.getFilePointer());
				if(posBegin <= 0)
				{
					return res;
				}
				else{
					file.seek(posBegin);
					for(int j=0;j<columnNum;j++){
						tmp = new resultLinks();
						tmp.setValue(file.readLine());
						tmp.setNext(null);
						last.setNext(tmp);
						last=tmp;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//This method searches for key terms, and return the result in a form of a linked list
	public long search(String obj, int column, long pos){
		int columnNum = -1;
		long posBegin = pos;
		if(file == null){
			return 0;
		}
		try {
			//
			if(file.length() == 0)
				return 0;
			file.seek(0);
			columnNum = file.readInt();
			file.readLine();
			if(posBegin != 0)
				file.seek(posBegin);
			String str = "";
			while(!str.equals(endOfFile)&&str!=null){
				posBegin = file.getFilePointer();
				
				for(int i=1;i<columnNum+1;i++){
					str = file.readLine();
					if(str==null)
						return 0;
					if(str.equals(endOfFile)){
						return 0;
						}
					//
					if(i == column && str.equals(obj)){
						return posBegin;
					}
				}
			}
			//
			return -2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public long searchEnd(){
		if(file == null){
			return 0;
		}
		try {
			if(file.length() == 0){
				return 0;
			}
			else {
				file.seek(0);
				file.readInt();
				long endpos = file.readLong();
				return endpos;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	
	public boolean insert(IDataObject data,long pos){
		if(file == null){
			return false;
		}
		try {
			String[] info = data.packetInfo();
			int lineNum = info.length;
			long filelength = 0;
			if(file.length() == 0){
				file.seek(0);
				file.writeInt(lineNum);
				file.writeLong(filelength);
				file.writeBytes("\r\n");
				for(int i=0;i<lineNum;i++){
					file.write(info[i].getBytes());
					file.writeBytes("\r\n");
				}
				long tmppos = file.getFilePointer();
				file.seek(0);
				file.readInt();
				file.writeLong(tmppos);
				file.seek(tmppos);
				file.write(endOfFile.getBytes());
				file.writeBytes("\r\n");
				return true;
			}
			long fileEnd = searchEnd();
			if(pos >= fileEnd){
				file.seek(fileEnd);
				for(int i=0;i<lineNum;i++){
					file.write(info[i].getBytes());
					file.writeBytes("\r\n");
				}
				long tmppos = file.getFilePointer();
				file.seek(0);
				file.readInt();
				//
				file.writeLong(tmppos);
				file.seek(tmppos);
				file.write(endOfFile.getBytes());
				file.writeBytes("\r\n");
				return true;
			}
			else{
				//
				file.seek(pos);
				int l = (int)fileEnd - (int)pos;
				byte[] tmp = new byte[l];
				file.readFully(tmp);
				file.seek(pos);
				for(int i=0;i<lineNum;i++){
					file.write(info[i].getBytes());
					file.writeBytes("\r\n");
				}
				
				//
				file.write(tmp);
				long tmppos = file.getFilePointer();
				file.seek(0);
				file.readInt();
				//
				file.writeLong(tmppos);
				file.seek(tmppos);
				file.write(endOfFile.getBytes());
				file.writeBytes("\r\n");
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	long find(int obj,boolean isAsc,int column){
		if(file == null){
			return -1;
		}
		try {
			if(file.length() == 0){
				return -2;
			}
			file.seek(0);
			int columnNum = file.readInt();
			file.readLine();
			while(true){
				long beginPos = file.getFilePointer();
				for(int i=1;i<=columnNum;i++){
					String str = file.readLine();
					if(str.equals(endOfFile)&&str!=null){
						return -2;
					}
					if(i == column){
						int tmp = Integer.parseInt(str);
						if((!isAsc)&&obj<=tmp)
							return beginPos;
						if(isAsc&&obj>=tmp)
							return beginPos;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean insertSortFile(IDataObject data, int column, boolean isAsc){
		String[] str = data.packetInfo();
		int obj = Integer.parseInt(str[column-1]);
		long pos = find(obj, isAsc, column);
		if(pos <= 0)
			pos = searchEnd();
		if(!insert(data, pos)){
			return true;
		}
		else{
			return false;
		}
	}
	

	public boolean modifyFile(IDataObject obj, long pos, int columnNum){
		if(file == null){
			return false;
		}
		try {
			if(file.length() == 0||pos<0||pos>file.length()){
				return false;
			}
			if(pos == 0)
			{
			    file.seek(0);
				file.readLine();
				pos = file.getFilePointer();
			 }
			file.seek(pos);
			for(int i =0;i<columnNum;i++){
				file.readLine();
			}
			
			long fileEnd = searchEnd();
			int l = (int)fileEnd - (int)file.getFilePointer();
			byte[] tmp = new byte[l];
			file.readFully(tmp);
			
			file.seek(pos);
			for(int i=0;i<columnNum;i++){
				file.writeBytes(obj.packetInfo()[i]);
				file.writeBytes("\r\n");
			}
			
			file.write(tmp);
			fileEnd = file.getFilePointer();
			file.seek(0);
			file.readInt();
			file.writeLong(fileEnd);
			file.seek(fileEnd);
			file.write(endOfFile.getBytes());
			file.writeBytes("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean modifyOrderFile(IDataObject obj, long pos, int columnNum, boolean isAsc){
		if(file == null){
			return false;
		}
		if(!deleteFile(pos))
			return false;
		if(insertSortFile(obj, columnNum, isAsc))
			return false;
		return true;
	}
	
	public boolean deleteFile(long pos){
		if(file == null){
			return false;
		}
		try {
			if(file.length()==0||pos<0||pos>file.length())
				return false;
			file.seek(0);
			int columnNum = file.readInt();
			file.readLine();
			long fileEnd = searchEnd();
			if(pos == 0)
				{
					file.seek(0);
					file.readLine();
					pos = file.getFilePointer();
				}
			file.seek(pos);
			for(int i=0;i<columnNum;i++){
				file.readLine();
			}
			int l = (int)fileEnd - (int)file.getFilePointer();
			byte[] tmp = new byte[l];
			file.readFully(tmp);
			file.seek(pos);
			file.write(tmp);
			long tmppos = file.getFilePointer();
			file.seek(0);
			file.readInt();
			file.writeLong(tmppos);
			file.seek(tmppos);
			file.write(endOfFile.getBytes());
			file.writeBytes("\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public resultLinks readAll(){
		resultLinks res = new resultLinks();
		res.setNext(null);
		resultLinks tmp,last;
		last = res;
		if(file == null){
			return res;
		}
		try {
			if(file.length() == 0)
				return res;
			file.seek(0);
			int columnNum = file.readInt();
			res.setValue(columnNum+"");
			file.readLine();
			while(true){
				String str  =file.readLine();
				if(str == null||str.equals("end of file"))
					return res;
				tmp = new resultLinks();
				tmp.setValue(str);
				tmp.setNext(null);
				last.setNext(tmp);
				last = tmp;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRwMode() {
		return rwMode;
	}

	public void setRwMode(String rwMode) {
		this.rwMode = rwMode;
	}
	
	public boolean setPos(long pos){
		if(file == null){
			return false;
		}
		try {
			file.seek(pos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public long getLength(){
		long length = -1;
		if(file == null){
			return -1;
		}
		try {
			length = file.length();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return length;
	}
	
}
