import java.util.*;
import java.io.*;
public class CountConf {
    public static TreeMap<String,int[]> read(String filePath)
    {
        TreeMap<String,int[]>map = new TreeMap<>();
        try{
            File file = new File(filePath);
            InputStreamReader read = new InputStreamReader(
            new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
               line.trim();
               String city = "";
               boolean findcity = false;
               for(int i=0;i<line.length();i++){
            	    if(i+1<line.length()&&line.charAt(i)=='|'&&line.charAt(i+1)=='|'){
            	    	if(!findcity){
            	    		findcity = true;
            	    		city = line.substring(0,i);
            	    		line = line.substring(i+2,line.length());
            	    	}
            	    	
            	    }
               }
              city.trim();
               List<String>s2 = new ArrayList<>();
               String c ="";
               for(int i=0;i<line.length();i++){
            	   if(line.charAt(i)=='|'){
            		   s2.add(c);
            		   c = "";
            	   }
            	   else c = c + line.charAt(i);
               }
               if(line.charAt(line.length()-1)!='|')s2.add(c);
               int year[] = new int[8];
               for(String conf:s2){
            	   if(conf.contains("2010")) year[0]++;
            	   if(conf.contains("2011")) year[1]++;
            	   if(conf.contains("2012")) year[2]++;
            	   if(conf.contains("2013")) year[3]++;
            	   if(conf.contains("2014")) year[4]++;
            	   if(conf.contains("2015")) year[5]++;
            	   if(conf.contains("2016")) year[6]++;
            	   if(conf.contains("2017")) year[7]++;
               }
               map.put(city,year);
            }
            bufferedReader.close();
            read.close();
        }
        catch (Exception e){
            System.out.println("error");
            e.printStackTrace();
        }
        for(String key:map.keySet()){
        	int y[] = map.get(key);
        	System.out.println(key+" "+y[0]+" "+y[1]+" "+y[2]+" "+y[3]+" "+y[4]+" "+y[5]+" "+y[6]+" "+y[7]);
        }
        return map;
    }
    private static boolean number(char a){
        return a>='0'&&a<='9';
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CountConf  c = new CountConf();
        c.read("D:/CS235DATA/q4.txt");
	}

}