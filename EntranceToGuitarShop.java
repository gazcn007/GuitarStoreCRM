import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import util.LinkOperation;
import util.recordLinks;
import util.resultLinks;
import crud.fileOperation;
import data.Record;


public class EntranceToGuitarShop {
    public EntranceToGuitarShop()
    {
        management mana = new management();//we can shorten the codes
        String s = null;
        System.out.println("welcome to GuitarManageSystem!");
        while (true) {
            printMessage();
            try {
                s = input();
                if (s.equals("quit"))
                    break;
                if (s.equals("1")) {
                    System.out.println("Please type into purchase info");
                    System.out.print("Guitar name£º");
                    s = input();
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    String nameString = s;
                    System.out.print("Purchase date£º");
                    s = input();
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                     String dateString = s;
                     int priceInt=0;
                     boolean check=true;
                    while (check==true)
                    {
                        
                   
                    System.out.print("Purchase price£º");
                    s = input(); 
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    try 
                    {
                     priceInt = Integer.parseInt(s);
                     check=false;
                    } catch (Exception e) {
                    System.out.println("input error:please re-input!");
                    }
                   
                }
                
                    check=true;
                    int numInt = 0;
                    while (check==true)
                    {
                    System.out.print("Purchase Amount£º");
                    s = input(); 
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    try 
                    {
                     numInt = Integer.parseInt(s);
                     check=false;
                    } catch (Exception e) {
                    System.out.println("input error:please re-input!");
                    }
                  }
                  
                    Record in = new Record(nameString, dateString, numInt, priceInt);
                    mana.in(in);
                } else if (s.equals("2")) {
                    System.out.print("Guitar name£º");
                    s = input();
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    String nameString = s;
                    System.out.print("Sale date£º");
                    s = input();
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    String dateString = s;
                    int priceInt=0;
                     boolean check=true;
                    while (check==true)
                    {
                        
                   
                    System.out.print("Selling price£º");
                    s = input(); 
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    try 
                    {
                     priceInt = Integer.parseInt(s);
                     check=false;
                    } catch (Exception e) {
                    System.out.println("input error:please re-input!");
                    }
                   
                }
                
                    check=true;
                    int numInt = 0;
                    while (check==true)
                    {
                    System.out.print("Selling Amount£º");
                    s = input(); 
                    if(s == "")
                    {
                        System.out.println("input error:cannot be empty!");
                        continue;
                    }
                    try 
                    {
                     numInt = Integer.parseInt(s);
                     check=false;
                    } catch (Exception e) {
                    System.out.println("input error:please re-input!");
                    }
                  }
                    Record out = new Record(nameString, dateString, numInt,
                            priceInt);
                    mana.sale(out);
                } else if (s.equals("3")) {
                    printScanChoice();
                    s = input();
                    if (s.equals("p")) {
                        System.out.println("Purchase record as follow");
                        mana.displayAll("in.guitar");
                    } else if (s.equals("s")) {
                        System.out.println("Sale record as follow");
                        mana.displayAll("out.guitar");
                    } else if (s.equals("g")) {
                        System.out.println("Guitar info as follow");
                        mana.displayAll("record.guitar");
                    } else {
                        System.out.println("There is no this choice!");
                    }
                }
                else if(s.equals("5")){
                    printStaticsInfo();
                    String staticType = input();
                    System.out.println("1: Ascending order");
                    System.out.println("2: descending order");
                    String orderType = input();
                    boolean isAsc = true;
                    if(orderType.equals("1"))
                        isAsc=true;
                    else if(orderType.equals("2"))
                        isAsc=false;
                    else {
                        System.out.println("input error!");
                    }
                    
                    fileOperation fileOp = new fileOperation();
                    fileOp.setFileName("record.guitar");
                    fileOp.setRwMode("r");
                    fileOp.openFile();
                    resultLinks res = fileOp.readAll();
                    fileOp.closeFile();
                    LinkOperation linkOp = new LinkOperation();
                    //stock
                    if(staticType.equals("1"))
                    {
                        recordLinks recLinks = linkOp.generateLink(res, 2);
                        linkOp.display(linkOp.rank(recLinks, 2, isAsc));
                    }
                    //profit
                    else if(staticType.equals("2"))
                    {
                        recordLinks recLinks = linkOp.generateLink(res, 2);
                        linkOp.display(linkOp.rank(recLinks, 5, isAsc));
                    }
                    //saleVolumne
                    else if(staticType.equals("3"))
                    {
                        recordLinks recLinks = linkOp.generateLink(res, 2);
                        linkOp.display(linkOp.rank(recLinks, 4, isAsc));
                    }
                    else {
                        System.out.println("Input error!");
                    }
                }
                else if(s.equals("4")){
                    printSearchChoice();
                    String searchScope = input();
                    System.out.print("Please input guitar name:");
                    String name = input();
                    if(searchScope.equals("p")){
                        fileOperation fileOp = new fileOperation();
                        fileOp.setFileName("in.guitar");
                        fileOp.setRwMode("r");
                        fileOp.openFile();
                        resultLinks res = fileOp.search(name, 1);
                        fileOp.closeFile();
                        LinkOperation linkOp = new LinkOperation();
                        System.out.println("Search result as follow\n");
                        linkOp.display(linkOp.generateLink(res, 1));
                    }
                    else if(searchScope.equals("s")){
                        fileOperation fileOp = new fileOperation();
                        fileOp.setFileName("out.guitar");
                        fileOp.setRwMode("r");
                        fileOp.openFile();
                        resultLinks res = fileOp.search(name, 1);
                        fileOp.closeFile();
                        LinkOperation linkOp = new LinkOperation();
                        System.out.println("Search result as follow\n");
                        linkOp.display(linkOp.generateLink(res, 1));
                    }
                    else if(searchScope.equals("g")){
                        fileOperation fileOp = new fileOperation();
                        fileOp.setFileName("record.guitar");
                        fileOp.setRwMode("r");
                        fileOp.openFile();
                        resultLinks res = fileOp.search(name, 1);
                        fileOp.closeFile();
                        LinkOperation linkOp = new LinkOperation();
                        System.out.println("Search result as follow\n");
                        linkOp.display(linkOp.generateLink(res, 2));
                    }
                    else {
                        System.out.println("Input error!");
                    }
                }
            } catch (Exception e) {
                break;
            }
        }
    }
    
    public static void printMessage(){
        //System.out.println("welcome to GuitarManageSystem!");
        System.out.println("FUNCTION LIST");
        System.out.println("1: Insert purchase record");
        System.out.println("2: Insert sale record");
        System.out.println("3: Scan info");
        System.out.println("4: Search info");
        System.out.println("5: statics");
        System.out.println("quit: Leave system!");
        System.out.println("Please enter your choice: ");
    }
    
    public static void printScanChoice(){
        System.out.println("Info type as follow");
        System.out.println("p: Purchase Record");
        System.out.println("s: Sale Record");
        System.out.println("g: Guitar Info");
        System.out.println("Enter choice,scan the info:");
    }
    
    public static void printSearchChoice(){
        System.out.println("Search scope as follow");
        System.out.println("p: Purchase Record");
        System.out.println("s: Sale Record");
        System.out.println("g: Guitar Info");
        System.out.println("Enter choice,choose the search scope:");
    }
    
    public static void printStaticsInfo(){
        System.out.println("statics type as follow");
        System.out.println("1: rank according to stock amount");
        System.out.println("2: rank according to totalprofit");
        System.out.println("3: rank according to amount sold");
        System.out.println("Enter choice,choose the statics type:");
    }
    
    private static String input(){
        String s = new String();
        InputStreamReader inStream = new InputStreamReader (System.in);
        BufferedReader text = new BufferedReader (inStream);
        try {
            s = text.readLine();
            return s;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }   
    }
}
