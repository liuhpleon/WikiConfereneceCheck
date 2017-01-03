/*Part 1, craw the data on wikicfp, for 4 category
 * Write by Haopeng Liu
 * */
import java.net.*;
import java.io.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.*;
class CollectionData{
	private static int DELAY = 7;
	private String category;
	private String url;
	/*init part, in order to init this program, we need to input the name of the category
	 *for example, if you want to search data mining, the category should be "data mining"
	 */
	public CollectionData(String category){
		this.category = category;
		url = "http://www.wikicfp.com/cfp/call?conference=";
	}
	/* this part is a part to  save all the information as a html format in order
	 * for further use
	 * */
	public void saveInfo(){
		try{
		    File f = new File("D:/CS235DATA/"+category+".txt");
		    if(!f.exists()){ 
		    	f.createNewFile();
		    }
		    OutputStream os = new FileOutputStream(f,true);
		    OutputStreamWriter w = new OutputStreamWriter(os);
		    List<String[]>allinfo = getConference();
            for(String info[]: allinfo){
             	String input = info[0]+"<*>"+info[1]+"<*>"+pickCity(info[2]);
             	w.write(input+"\r\n");
            }
            w.close();
        	os.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/*from our question we found we are not interested in the country,so I write a code to 
	 * clean the place so that it will only have city
	 * */
	public String pickCity(String s){
		String str = "";
		if(s.length()==0) return "";
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)==','||s.charAt(i)=='(') break;
			else str = str+s.charAt(i);
		}
		return str;
	}
	public List<String[]> getConference(){
		List<String[]>info = new ArrayList<>();
     	for(int i=1;i<=20;i++){
     		try{
	    	    String link = url+URLEncoder.encode(category, "UTF-8") +"&page=" + i;
	    	    info.addAll(getInfo(link));
	    	    Thread.sleep(DELAY*1000);
     		}catch(Exception e){
     			System.out.println("read error");
     		}
	    }
     	return info;
	}
	
   /* this part is how to get information we need from the page, first is select the table by selector
    * as we observe the page, the first line is the head like "when where" sth like that
    * this part should be discard
    * and for the odd part we need the acronym and name and even for the place
    * we use an array with size 3 to store that element
    * and using a list to return this result
	*/
	public List<String[]> getInfo(String link){
		List<String[]>list = new ArrayList<>();
		try{
		    Document doc = Jsoup.connect(link).get();
		    Element tr = doc.select("body > div.contsec > center > form > table > tbody > tr:nth-child(3) > td > table > tbody").get(0);
			String str[] = new String[3];
			int i = 0;
			for(Element info:tr.children()){
				if(i!=0){ 
					if(info.text().equals("Expired CFPs")) i++;
					else{
						if(i%2==1){
							Element name = info.child(0);
							Element detail = info.child(1);
							str[0] = name.text();
							str[1] = detail.text();
						}
						else if(i%2==0){
				        	Element place = info.child(1);
				        	str[2] = place.text();
				        	list.add(str);
				        	str = new String[3];
				        }
					}
				}
				i++;
		    }
		}catch(Exception e){
			System.out.println(e);
		}
		return list;
	}
	//main function. run this program
	public static void main(String args[]){
		//CollectionData dm = new CollectionData("data mining");
		//CollectionData db = new CollectionData("databases");
		//CollectionData ai = new CollectionData("artificial intelligence");
		CollectionData ml = new CollectionData("machine learning");
		try{
		    //dm.saveInfo();
		    //db.saveInfo();
		    //ai.saveInfo();
		    ml.saveInfo();
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

