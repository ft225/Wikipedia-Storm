package bolt;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PageCounter implements IRichBolt {
    Integer id;
    String name;
    Map<String, Integer> counts = new HashMap<String, Integer>();
    private OutputCollector collector;
      
    JSONObject obj = new JSONObject();
	JSONObject objar = new JSONObject();
	JSONArray list1 = new JSONArray();
 
    public Map getComponentConfiguration() {return null;}
 
    /**
     * At the end of the spout (when the cluster is shutdown
     * We will show the word counters
     */
    @Override
    public void cleanup() {
       
        try {
        for(String w : counts.keySet()){
        	JSONObject json = new JSONObject();
        	json.put("name", w);
        	json.put("size",counts.get(w));
        	list1.add(json);
        }
        	obj.put("name", "flare");
        	obj.put("children", list1);
        	
        	FileWriter file = new FileWriter(System.getProperty("user.dir") +"/WebContent/flare.json");
    		file.write(obj.toJSONString());
    		file.flush();
    		file.close();
     
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    
    }
 
    /**
     * On each word We will count
     */
    @Override
    public void execute(Tuple tuple) {
 
        String word = tuple.getString(0);
        Integer count = counts.get(word);
        if(count==null) count = 0;
        count++;
        counts.put(word, count);
        
        collector.emit(new Values(word, count));
          
        //Set the tuple as Acknowledge
        collector.ack(tuple);
    }
 
 
    /**
     * On create
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context,
            OutputCollector collector) {
        this.counts = new HashMap<String, Integer>();
        this.collector = collector;
        this.name = context.getThisComponentId();
        this.id = context.getThisTaskId();
           	
 
    }
 
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {declarer.declare(new Fields("user", "count"));}}
