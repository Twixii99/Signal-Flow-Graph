package eg.edu.alexu.csd.filestructure.mason;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class GraphDrawing {

    private Graph signalFlowGraph;
    private int[] addresses = new int[MainData.numOfNodes+1];

    public GraphDrawing() {
        this.signalFlowGraph = new SingleGraph("Signal Flow Grapgh");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        this.signalFlowGraph.addAttribute("ui.antialias");
        this.signalFlowGraph.addAttribute("ui.quality");
        this.signalFlowGraph.setAttribute("ui.stylesheet", "node:clicked {\n" +
                "    fill-color: purple;\n" +
                "    text-size:    16;\n" +
                "    text-style:   bold;\n" +
                "    text-color:   #FFF;\n" +
                "    text-alignment: at-right; \n" +
                "    text-padding: 3px, 2px; \n" +
                "    text-background-mode: rounded-box; \n" +
                "    text-background-color: #A7CC; \n" +
                "    text-color: white; \n" +
                "    text-offset: 5px, 0px; \n" +
                "}\n" +
                "\n" +
                "node {\n" +
                "    size:         25px;\n" +
                "    shape:        circle;\n" +
                "    fill-color:   #8facb4;\n" +
                "    stroke-mode:  plain;\n" +
                "    stroke-color: black;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "edge {\n" +
                "    size:           4px;\n" +
                "    fill-mode:      plain;\n" +
                "    shape:          angle;\n" +
                "    text-size:      20px;\n" +
                "}");
        this.signalFlowGraph.display();
    }

    public void addNode(String node) {
        this.signalFlowGraph.addNode(node).addAttribute("ui.label", "V" + node);
    }

    public void removeNode(String node) {
        this.signalFlowGraph.removeNode(node);
    }

    public Edge addEdge(String edge, String from, String to, double gain) {
        Edge edge1 = this.signalFlowGraph.addEdge(edge, from, to, true);
        edge1.addAttribute("ui.label", "E" + gain);
        return edge1;
    }

    public void addEdge(int start, int end, double gain) {
        String strStart = String.valueOf(start);
        String strEnd = String.valueOf(end);
        String edgex = strStart + strEnd;
        Edge edge1 = this.signalFlowGraph.addEdge(edgex, strStart, strEnd, true);
        edge1.addAttribute("ui.label", "E" + gain);
    }

    public void removeEdge(String start, String end) {
        this.signalFlowGraph.removeEdge(start, end);
    }

    public void removeAllEdges(String node) {
        this.signalFlowGraph.removeNode(node);
        this.addNode(node);
    }
}