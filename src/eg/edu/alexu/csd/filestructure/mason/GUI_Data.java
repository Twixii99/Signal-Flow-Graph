package eg.edu.alexu.csd.filestructure.mason;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI_Data extends JFrame {
    private final String FRAME_TITLE = "Getting data";
    private final String[] labels = {"SOURCE NODE: ", "DESTINATION NODE: ", "BRANCH GAIN: ", "TOTAL GAIN: "};

    private JPanel[] subPanels = new JPanel[5];
    private JLabel[] titals = new JLabel[4];
    private JTextField[] input = new JTextField[4];
    private JButton gain = new JButton("GAIN"), enter = new JButton("ENTER");

    private final Border border = BorderFactory.createLineBorder(Color.BLACK);
    private final Font DEFAULT_FONT = new Font("TimesRoman", Font.BOLD, 15);

    private final Mason masonLogic = new Mason();
    boolean validInput = true;

    public GUI_Data() {
        JOptionPane.showMessageDialog(this, "Note: Source node is at index 1");
        super.setTitle(this.FRAME_TITLE);
        super.setLayout(new GridLayout(5,1,0,0));
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setSize(new Dimension(500, 600));
        super.setLocationRelativeTo(null);
        this.setComponents();
        super.setVisible(true);
    }

    private void setComponents() {
        this.makeDefinitions();
        this.addLogic();
    }

    private void makeDefinitions() {
        for (int i = 0; i < 4; ++i) {
            this.subPanels[i] = new JPanel(null);
            this.titals[i] = new JLabel(this.labels[i]);
            this.input[i] = new JTextField();
        }

        for (int i = 0; i < 4; ++i) {
            this.subPanels[i].add(this.titals[i]);
            this.subPanels[i].add(this.input[i]);
        }

        for (int i = 0; i < 4; ++i) {
            this.titals[i].setBounds(50, 45, 200, 30);
            this.titals[i].setFont(this.DEFAULT_FONT);
            this.input[i].setBounds(270, 25, 200, 60);
            this.input[i].setFont(this.DEFAULT_FONT);
            this.input[i].setHorizontalAlignment(JTextField.CENTER);
            this.input[i].setBorder(BorderFactory.createCompoundBorder(border,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        }

        this.subPanels[4] = new JPanel(null);
        this.subPanels[4].add(this.enter);
        this.enter.setBounds(45, 25, 200, 60);
        this.enter.setBackground(Color.BLACK);
        this.enter.setForeground(Color.WHITE);
        this.enter.setFont(this.DEFAULT_FONT);
        this.subPanels[4].add(this.gain);
        this.gain.setBounds(255, 25, 200, 60);
        this.gain.setBackground(Color.BLACK);
        this.gain.setForeground(Color.WHITE);
        this.gain.setFont(this.DEFAULT_FONT);

        for (int i = 0; i < 5; ++i)
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
            } if(!input[2].getText().trim().matches("([0-9]+\\.?[0-9]*)")) {
                input[2].setText("#Float.");
                validInput = false;
            }
            int start = Integer.parseInt(input[0].getText().trim()),
                    end = Integer.parseInt(input[1].getText().trim());
            if( start > MainData.numberOfNodes || start < 0 ||
                            end > MainData.numberOfNodes || end < 0) {
                JOptionPane.showMessageDialog(this,
                        "Ooops, Out of Bounds You have only " +
                                MainData.numberOfNodes + " nodes (0 < n < " + MainData.numberOfNodes + ")");
                for(JTextField text: input)
                    text.setText("");
                validInput = false;
            }
            if(validInput) {
                MainData.addData(start, end, Double.parseDouble(input[2].getText().trim()));
                for(JTextField text: input)
                    text.setText("");
            }
        });

        this.gain.addActionListener(actionEvent -> {
            MainData.putSourceAndSinkNodes();
        });

        for(JTextField text: this.input) {
            text.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(!validInput) {
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
