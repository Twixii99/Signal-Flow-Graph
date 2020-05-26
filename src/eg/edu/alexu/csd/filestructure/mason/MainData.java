package eg.edu.alexu.csd.filestructure.mason;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainData {
    public static Vector<Vector<XPoint>> mainData = new Vector<>();
    public static Vector<DataFormat> paths = new Vector<>();
    public static Vector<DataFormat> loops = new Vector<>();
    public static int numberOfNodes;

    public static class XPoint {
        public Integer destination;
        public Double gain;

        public XPoint(Integer destination, Double gain) {
            this.destination = destination;
            this.gain = gain;
        }
    }

    public static class DataFormat {
        public String nodeCollections;
        public Double gain;

        public DataFormat(String nodeCollections, Double gain) {
            this.nodeCollections = nodeCollections;
            this.gain = gain;
        }
    }

    public static void initialize() {
        for(int i = 0; i <= numberOfNodes+1; ++i)
            mainData.add(new Vector<>());
    }

    public static void addData(Integer start, Integer end, Double gain) {
        mainData.get(start).add(new XPoint(end, gain));
    }

    public static void print() {
        for(Vector<XPoint> x : mainData)
            for(XPoint y : x)
                System.out.printf("%d\t%f", y.destination, y.gain);
    }

    public static void putSourceAndSinkNodes() {
        Vector<XPoint> source = new Vector<>();
        source.add(new XPoint(1,  1.0));
        mainData.set(0, source);
        mainData.get(numberOfNodes).add(0, new XPoint(numberOfNodes+1, 1.0));
    }

    public static void clear() {
        MainData.mainData.clear();
        MainData.paths.clear();
        MainData.loops.clear();
    }
}
