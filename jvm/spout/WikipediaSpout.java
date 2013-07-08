package spout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
 
import starter.Wiki.Revision;
import util.WikipediaConnector;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
 
public class WikipediaSpout extends BaseRichSpout {
     
    SpoutOutputCollector _collector;
    Revision[] revs = null;
    int i =0;
    String _username;
    String _pwd;
     
    public WikipediaSpout(String username, String pwd) {
        _username = username;
        _pwd = pwd;
    }
     
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
         
        _collector = collector;
        WikipediaConnector connector = new WikipediaConnector();
           
            try {
				connector.connect(_username,_pwd);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
         
             
           try {
                revs = connector.getChanges();
                }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
 
    @Override
    public void nextTuple() {
        i++;
        Utils.sleep(100);
        String page = revs[i].getPage();
        String usr = revs[i].getUser();
        
        _collector.emit(new Values(page,usr));
         
    }
 
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("page","user"));
         
    }
 
}