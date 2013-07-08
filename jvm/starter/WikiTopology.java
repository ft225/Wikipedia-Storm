package starter;


import spout.WikipediaSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import bolt.PageCounter;
import bolt.UserCounter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

/**
* This topology demonstrates Storm's stream groupings and multilang capabilities.
*/
public class WikiTopology {

	public static void main(String[] args) throws Exception{
    	WikiTopology wikiTopology = new WikiTopology();
		final Socket socket = new Socket(wikiTopology);
		final URI uri = new URI("file://"+ System.getProperty("user.dir") +"/WebContent/index.html");
        class OpenUrlAction implements ActionListener {
          @Override public void actionPerformed(ActionEvent e) {
            open(uri);
          }
        }
        JFrame frame = new JFrame("Links");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 400);
        Container container = frame.getContentPane();
        container.setLayout(new GridBagLayout());
        JButton button = new JButton();
        button.setText("<HTML>Click the <FONT color=\"#000099\"><U>link</U></FONT>"
            + " to go to the SunsetWiki website.</HTML>");
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setBackground(Color.WHITE);
        button.setToolTipText(uri.toString());
        button.addActionListener(new OpenUrlAction());
        container.add(button);
        frame.setVisible(true);
      }

      private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
          try {
            Desktop.getDesktop().browse(uri);
          } catch (IOException e) { /* TODO: error handling */ }
        } 
        else { /* TODO: error handling */ }
    	

      }
public static void executeStormUser() throws Exception {

final TopologyBuilder builder = new TopologyBuilder();

builder.setSpout("spout", new WikipediaSpout("ft227","Matteo"));

builder.setBolt("countUser", new UserCounter())
.fieldsGrouping("spout", new Fields("user"));

final Config conf = new Config();
final LocalCluster cluster = new LocalCluster();

cluster.submitTopology("test", conf, builder.createTopology());


Thread.sleep(10000);

cluster.shutdown();
}

public static void executeStormPage() throws Exception {

	final TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("spout", new WikipediaSpout("ft227","Matteo"));

			builder.setBolt("countPage", new PageCounter())
				.fieldsGrouping("spout", new Fields("page"));

			final Config conf = new Config();
			final LocalCluster cluster = new LocalCluster();

			cluster.submitTopology("test", conf, builder.createTopology());


			Thread.sleep(10000);

			cluster.shutdown();
		}

}