package eg.edu.alexu.csd.filestructure.mason;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI_Data extends JFrame {
    private final String FRAME_TITLE = "Getting data";
    private final String[] labels = {"SOURCE NODE: ", "DESTINATION NODE: ", "BRANCH GAIN: ", "Remove Node: ", "TOTAL GAIN: "};

    private JPanel[] subPanels = new JPanel[6];
    private JLabel[] titals = new JLabel[5];
    private JTextField[] input = new JTextField[5];
    private JButton gain = new JButton("GAIN"), enter = new JButton("ENTER"), clear = new JButton("DELETE");

    private final Border border = BorderFactory.createLineBorder(Color.BLACK);
    private final Font DEFAULT_FONT = new Font("TimesRoman", Font.BOLD, 15);

    boolean validInput = true;
    private boolean validClearInput = true;

    private GraphDrawing graphDrawing;

    public GUI_Data() {
        JOptionPane.showMessageDialog(this, "Note: Source node is at index 1");
        this.graphDrawing = new GraphDrawing();
        super.setTitle(this.FRAME_TITLE);
        super.setLayout(new GridLayout(6,1,0,0));
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setSize(new Dimension(500, 720));
        super.setLocationRelativeTo(null);
        this.setComponents();
        super.setVisible(true);
    }

    private void setComponents() {
        this.makeDefinitions();
        this.addLogic();
    }

    private void makeDefinitions() {
        for (int i = 1; i <= MainData.numOfNodes; ++i)
            this.graphDrawing.addNode(String.valueOf(i));

        for (int i = 0; i < 5; ++i) {
            this.subPanels[i] = new JPanel(null);
            this.titals[i] = new JLabel(this.labels[i]);
            this.input[i] = new JTextField();
        }

        for (int i = 0; i < 5; ++i) {
            this.subPanels[i].add(this.titals[i]);
            this.subPanels[i].add(this.input[i]);
        }

        for (int i = 0; i < 5; ++i) {
            this.titals[i].setBounds(50, 45, 200, 30);
            this.titals[i].setFont(this.DEFAULT_FONT);
            this.input[i].setBounds(270, 25, 200, 60);
            this.input[i].setFont(this.DEFAULT_FONT);
            this.input[i].setHorizontalAlignment(JTextField.CENTER);
            this.input[i].setBorder(BorderFactory.createCompoundBorder(border,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }

        this.subPanels[5] = new JPanel(null);
        this.subPanels[5].add(this.enter);
        this.enter.setBounds(10, 25, 154, 60);
        this.enter.setBackground(Color.BLACK);
        this.enter.setForeground(Color.WHITE);
        this.enter.setFont(this.DEFAULT_FONT);
        this.subPanels[5].add(this.gain);
        this.gain.setBounds(170, 25, 160, 60);
        this.gain.setBackground(Color.BLACK);
        this.gain.setForeground(Color.WHITE);
        this.gain.setFont(this.DEFAULT_FONT);
        this.subPanels[5].add(this.clear);
        this.clear.setBounds(335, 25, 150, 60);
        this.clear.setBackground(Color.BLACK);
        this.clear.setForeground(Color.WHITE);
        this.clear.setFont(this.DEFAULT_FONT);

        for (int i = 0; i < 6; ++i)
            super.add(this.subPanels[i]);
    }

    private void addLogic() {
        this.enter.addActionListener(actionEvent -> {
            if(!input[0].getText().trim().matches("[0-9]+")) {
                input[0].setText("#Integer.");
                validInput = false;
            } if(!input[1].getText().trim().matches("[0-9]+")) {
                input[1].setText("#Integer.");
                validInput = false;
            } if(!input[2].getText().trim().matches("([0-9]*\\.?[0-9]+)")) {
                input[2].setText("#Float.");
                validInput = false;
            }
            if(this.validInput && this.input[0].getText() != "" && this.input[1].getText() != "") {
                int start = Integer.parseInt(input[0].getText().trim()),
                        end = Integer.parseInt(input[1].getText().trim());
                if (start > MainData.numOfNodes || start < 1 ||
                        end > MainData.numOfNodes || end < 1) {
                    JOptionPane.showMessageDialog(this,
                            "Ooops, Out of Bounds You have only " +
                                    MainData.numOfNodes + " nodes (0 < n <= " + MainData.numOfNodes + ")");
                    for (JTextField text : input)
                        text.setText("");
                    validInput = false;
                }
                if (validInput) {
                    MainData.segmentsGains[start - 1][end - 1] = Double.parseDouble(input[2].getText().trim());
                    this.graphDrawing.addEdge(start, end, MainData.segmentsGains[start - 1][end - 1]);
                    for (JTextField text : input)
                        text.setText("");
                } else this.validInput = true;
            }
        });

        this.clear.addActionListener(actionEvent -> {
            if(!input[3].getText().trim().matches("[0-9]+\\s*((->)\\s*[0-9]+)?")) {
                input[3].setText("#INT|->INT.");
                this.validClearInput = false;
            }
            Integer clear;
            if(this.validClearInput) {
                if(!this.input[3].getText().contains("->")) {
                    clear = Integer.parseInt(this.input[3].getText().trim());
                    for (int i = 0; i < MainData.numOfNodes; ++i) {
                        MainData.segmentsGains[i][clear-1] = 0;
                        MainData.segmentsGains[clear-1][i] = 0;
                        this.graphDrawing.removeAllEdges(String.valueOf(clear));
                    }
                } else {
                    String[] str = this.input[3].getText().trim().split("->");
                    MainData.segmentsGains[Integer.parseInt(str[0])-1][Integer.parseInt(str[1])-1] = 0;
                    this.graphDrawing.removeEdge(str[0], str[1]);
                }
            }
            input[3].setText("");
            input[4].setText("");
        });

        this.gain.addActionListener(actionEvent -> {
            MasonEvaluator masonEvaluator = new MasonEvaluator();
            masonEvaluator.setSFG(MainData.segmentsGains);
            MainData.forwardPaths = masonEvaluator.getForwardPaths();
            MainData.loops = masonEvaluator.getLoops();
            MainData.nonTouchingloops = masonEvaluator.getNonTouchingLoops();
            MainData.overAllTF = masonEvaluator.getOvalAllTF();
            MainData.forwardPathsGain = masonEvaluator.getForwardPathGains();
            MainData.nonTouchingloopsGain = masonEvaluator.getNonTouchingLoopGains();
            this.input[4].setText(String.valueOf(MainData.overAllTF));
        });

        for(JTextField text: this.input) {
            text.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(!validInput) {
                        for(JTextField text : input)
                            text.setText("");
                        validInput = true;
                    }
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
        }
    }
}
