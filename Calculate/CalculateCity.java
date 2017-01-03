import java.util.*;
import java.io.*;
public class CalculateCity {
    public static TreeMap<Integer,String> read(String filePath)
    {
        TreeMap<Integer,String>map = new TreeMap<>();
        try{
            File file = new File(filePath);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
               line.trim();
               String number = "";
               String city = "";
               for(int i = 0;i<line.length();i++){
            	   if(line.charAt(i)>='0'&&line.charAt(i)<='9'&&i+3>line.length()){
            	       city = line.substring(0,i);
            	       for(int j = i;j<line.length();j++){
            	    	   if(line.charAt(j)>='0'&&line.charAt(j)<='9') number = number+""+line.charAt(j);
            	       }
            	       break;
            	      
            	   }
               }
               city.trim();
               number.trim();
               int num = Integer.parseInt(number);
               if(!map.containsKey(num))map.put(num,city);
               else map.put(num,map.get(num)+" "+city);
            }
            bufferedReader.close();
            read.close();
            while(map.size()>0){
            	int key = map.lastKey();
            	System.out.println(key+" "+map.get(key));
            	map.remove(key);
            }
        }
        catch (Exception e){
            System.out.println("error");
            e.printStackTrace();
        }
        return map;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        CalculateCity  c = new CalculateCity();
        c.read("D:/CS235DATA/q1.txt");
	}

}
