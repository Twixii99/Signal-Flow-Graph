package eg.edu.alexu.csd.filestructure.mason;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

public class GUI_Input extends JFrame {
    private final String FRAME_TITLE = "Taking number of nodes";
    private final String INPUT_LABEL = "Enter totla number of nodes!";

    private final Font DEFAULT_FONT = new Font("TimesRoman", Font.BOLD, 15);
    private final Border border = BorderFactory.createLineBorder(Color.BLACK);

    private JLabel label = new JLabel(this.INPUT_LABEL);
    private JTextField input = new JTextField();
    private JButton enter = new JButton("Enter");

    private boolean valid = true;

    public GUI_Input() {
        super.setTitle(this.FRAME_TITLE);
        super.setLayout(null);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setResizable(false);
        super.setSize(new Dimension(500, 200));
        super.setLocationRelativeTo(null);
        this.setComponents();
        super.setVisible(true);
    }

    private void setComponents() {
        this.setLabel();
        this.setTextArea();
        this.setEnterButton();
    }

    private void setLabel() {
        super.add(this.label);
        this.label.setBounds(130, 10, 500, 15);
        this.label.setFont(this.DEFAULT_FONT);
    }

    private void setEnterButton() {
        super.add(this.enter);
        this.enter.setBounds(155, 100,  180, 50);
        this.enter.setBackground(Color.BLACK);
        this.enter.setForeground(Color.WHITE);
        this.enter.addActionListener(actionEvent -> {
            if(!input.getText().trim().matches("[1-9]+")) {
                input.setText("+veNum");
                valid = false;
            } else {
                MainData.numberOfNodes = Integer.parseInt(input.getText().trim());
                MainData.initialize();
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                SwingUtilities.invokeLater(() -> new GUI_Data());
            }
        });
    }

    private void setTextArea() {
        super.add(this.input);
        this.input.setBounds(145, 40, 200, 50);
        this.input.setFont(new Font("TimesRoman", Font.BOLD, 30));
        this.input.setHorizontalAlignment(JTextField.CENTER);
        this.input.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.input.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(!valid) {
                    input.setText("");
                    valid = true;
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

    public static void main(String[] args) {
        GUI_Input gui_input = new GUI_Input();
    }
}