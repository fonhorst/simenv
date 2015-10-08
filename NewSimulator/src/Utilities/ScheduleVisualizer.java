package Utilities;

import Env.Entities.Node;
import Env.Entities.SchedItem;
import Env.Entities.Schedule;
import net.sf.jedule.JeduleStarter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Mishanya on 07.10.2015.
 */
public class ScheduleVisualizer {

    int counter;

    public ScheduleVisualizer() {
        counter = 0;
    }

    public void schedVisualize(Schedule sched) throws TransformerException {

        File tempDir = new File("./temp");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }

        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        sched = sched.clone();

        String cmapString =
                "<cmap name=\"default\">" +
                    "<conf name=\"min_font_size_label\" value=\"14\" />" +
                    "<conf name=\"font_size_label\" value=\"18\" />" +
                    "<conf name=\"font_size_axes\" value=\"18\" />" +

                    "<task id=\"waiting\">" +
                        "<color type=\"fg\" rgb=\"FFFFFF\" />" +
                        "<color type=\"bg\" rgb=\"0000FF\" />" +
                    "</task>" +

                    "<task id=\"executing\">" +
                        "<color type=\"fg\" rgb=\"FFFFFF\" />" +
                        "<color type=\"bg\" rgb=\"00FF00\" />" +
                    "</task>" +

                    "<composite>" +
                        "<task id=\"waiting\" />" +
                        "<task id=\"executing\" />" +
                        "<color type=\"fg\" rgb=\"FFFFFF\" />" +
                        "<color type=\"bg\" rgb=\"ff6200\" />" +
                    "</composite>" +
                "</cmap>";

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(cmapString));
        Document cmap = null;
        try {
            assert db != null;
            cmap = db.parse(is);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        
        Document jed = scheduleToXML(sched);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(cmap);
        StreamResult result = new StreamResult(new File("./temp/tempcmap.xml"));
        transformer.transform(source, result);

        source = new DOMSource(jed);
        result = new StreamResult(new File("./temp/tempjed.jed"));
        transformer.transform(source, result);

        String[] jedArgs = new String[12];
        jedArgs[0] = "-p";
        jedArgs[1] = "simgrid";
        jedArgs[2] = "-f";
        jedArgs[3] = "./temp/tempjed.jed";
        jedArgs[4] = "-d";
        jedArgs[5] = "320x480";
        jedArgs[6] = "-o";
        jedArgs[7] = "./temp/temppng" + counter + ".png";
        jedArgs[8] = "-gt";
        jedArgs[9] = "png";
        jedArgs[10] = "-cm";
        jedArgs[11] = "./temp/tempcmap.xml";

        JeduleStarter.main(jedArgs);
        counter++;
        try {
            Files.delete(Paths.get(jedArgs[3]));
            Files.delete(Paths.get(jedArgs[11]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document scheduleToXML(Schedule sched) {
        ArrayList<Node> nodes = new ArrayList<>();
        sched.getSchedule().keySet().forEach(nodes::add);

        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert db != null;

        Document doc = db.newDocument();

        Element grid_schedule = doc.createElement("grid_schedule");
        doc.appendChild(grid_schedule);

        Element grid_info = doc.createElement("grid_info");
        grid_schedule.appendChild(grid_info);

        Element clusters = doc.createElement("clusters");
        grid_info.appendChild(clusters);

        Element cluster = doc.createElement("cluster");
        cluster.setAttribute("id", "0");
        cluster.setAttribute("hosts", "" + nodes.size());
        cluster.setAttribute("first_host", "0");
        clusters.appendChild(cluster);

        Element node_infos = doc.createElement("node_infos");
        grid_schedule.appendChild(node_infos);

        for (int n = 0; n < nodes.size(); n++) {
            Node node = nodes.get(n);
            ArrayList<SchedItem> nodesSched = sched.getSchedule().get(node);
            nodesSched.add(0, node.getExecItem());
            for (SchedItem si : nodesSched) {
                if (si == null) {
                    continue;
                }
                Element node_statistics = doc.createElement("node_statistics");
                node_infos.appendChild(node_statistics);

                Element node_id = doc.createElement("node_property");
                node_id.setAttribute("name", "id");
                node_id.setAttribute("value", si.getTask().getName());

                Element node_type = doc.createElement("node_property");
                node_type.setAttribute("name", "type");
                if (nodesSched.indexOf(si) == 0) {
                    node_type.setAttribute("value", "executing");
                } else {
                    node_type.setAttribute("value", "waiting");
                }

                Element node_startTime = doc.createElement("node_property");
                node_startTime.setAttribute("name", "start_time");
                node_startTime.setAttribute("value", "" + si.getStartTime());

                Element node_endTime = doc.createElement("node_property");
                node_endTime.setAttribute("name", "end_time");
                node_endTime.setAttribute("value", "" + si.getEndTime());

                node_statistics.appendChild(node_id);
                node_statistics.appendChild(node_type);
                node_statistics.appendChild(node_startTime);
                node_statistics.appendChild(node_endTime);

                Element configuration = doc.createElement("configuration");
                node_statistics.appendChild(configuration);

                Element conf_cluster = doc.createElement("conf_property");
                conf_cluster.setAttribute("name", "cluster_id");
                conf_cluster.setAttribute("value", "0");

                Element conf_hosts = doc.createElement("conf_property");
                conf_hosts.setAttribute("name", "host_nb");
                conf_hosts.setAttribute("value", "1");

                configuration.appendChild(conf_cluster);
                configuration.appendChild(conf_hosts);

                Element host_lists = doc.createElement("host_lists");
                configuration.appendChild(host_lists);

                Element hosts = doc.createElement("hosts");
                hosts.setAttribute("start", "" + n);
                hosts.setAttribute("nb", "1");
                host_lists.appendChild(hosts);
            }
        }

        return doc;
    }

}
