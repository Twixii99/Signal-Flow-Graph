package eg.edu.alexu.csd.filestructure.mason;

import java.util.Scanner;

public class Mason {
    private StringBuilder path =  new StringBuilder("");
    private Double gain = 1.0;

    private final int sourceNodeIndex = 0;
    private final int sinkNodeIndex = MainData.numberOfNodes+1;

    private boolean pathFound = false;
    private boolean loopFound = false;

    private double[] dummyNeeded = new double[MainData.numberOfNodes+1];

    public Mason() {}

    public void startManipulation() {
        this.collectPassesWithLoops(this.sourceNodeIndex, 1.0);
    }

    private void collectPassesWithLoops(int node, double pathGain) {
        if(node == this.sinkNodeIndex) {
            this.path.delete(this.path.length()-1, this.path.length());
            this.path.delete(this.path.lastIndexOf(","), this.path.length());
            MainData.paths.add(new MainData.DataFormat(path.toString(), pathGain));
            this.path.append(',');
            this.pathFound = true;
        }

        for(int i = 0; i < MainData.mainData.get(node).size(); ++i) {
            if(this.pathFound) {
                this.path.delete(this.path.indexOf(",", this.path.indexOf(String.valueOf(node))) + 1, this.path.length());
                pathGain /= this.dummyNeeded[node];
                this.pathFound = false;
            }
            Integer distination = MainData.mainData.get(node).get(i).destination;
            this.dummyNeeded[node] = MainData.mainData.get(node).get(i).gain;
            pathGain *= this.dummyNeeded[node];
            this.path.append(distination).append(',');
            this.collectPassesWithLoops(distination, pathGain);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainData.numberOfNodes = 5;
        MainData.initialize();
        for(int i = 0; i < 7; ++i) {
            int x = scanner.nextInt(), y = scanner.nextInt(); Double z = scanner.nextDouble();
            MainData.mainData.get(x).add(new MainData.XPoint(y,z));
        }
        MainData.putSourceAndSinkNodes();
        Mason mason = new Mason();
        mason.startManipulation();
        for(MainData.DataFormat dataFormat : MainData.paths)
            System.out.printf("%-30.30s  %-30.30s%n", dataFormat.nodeCollections, dataFormat.gain);
    }
}
/*
1 2 0.12
2 3 1.25
2 2 3.789
3 4 5.232
4 3 0.14
4 5 4.19
1 4 3.167
2 3 12.5
2 4 12.5
3 5 0.76
5 2 3.789
 */