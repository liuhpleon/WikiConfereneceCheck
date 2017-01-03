import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/*Write by Haopeng Liu
the differnce of this code from q2 is 
we write(conference,city) instead of (city,conference)
and we discard the year of conference
*/
public class WordCount {

  public static class TokenizerMapper extends Mapper<Object, Text, Text,Text>{

    private Text word = new Text();
    private Text input  = new Text();
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        String city = "";
        String conf = "";
        for(int i=0;i<str.length();i++){
           if(number(str.charAt(i))){
              if(str.substring(Math.max(i-3,0),i).equals("201")){
                 conf = str.substring(0,i-3); //discard the year
                 city = str.substring(i+1,str.length());
				 conf.trim();
				 city.trim();
              }
           }
        }
        word.set(conf);
        input.set(city);  
        context.write(word,input);
    }
  }
  private static boolean number(char a){
      return a>='0'&&a<='9';
  }
  public static class IntSumReducer extends Reducer<Text,Text,Text,Text> {
    private Text result = new Text();
    public void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
      String sum = "";
      for (Text val : values) {
        sum =sum+" "+val.toString(); //append city
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}