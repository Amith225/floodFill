package floodFill.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FloodFillVisualizer extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private int selected = FloodFill.WALL;
    private FloodFill floodFill = new FloodFill(ROWS, COLS);
    private JButton[][] buts;

    private String conv(int a) {
        String s = "*";
        switch (a) {
            case FloodFill.WALL -> s = "#";
            case FloodFill.START -> s = "S";
            case FloodFill.END -> s = "E";
        }
        return s;
    }

    private void Grid() {
        JPanel frame = new JPanel();
        frame.setSize(25 * ROWS, 25 * COLS);
        frame.setLayout(new GridLayout(ROWS, COLS));
        buts = new JButton[ROWS][COLS];
        for (int i = 0; i < ROWS * COLS; i++) {
            final JButton button = new JButton(conv(floodFill.get(i % COLS, i / COLS)));
            buts[i / COLS][i % COLS] = button;
            final int j = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selected == FloodFill.START) {
                        floodFill.set(floodFill.xStart - 1, floodFill.yStart - 1, FloodFill.FREE);
                        floodFill.xStart = j % COLS + 1;
                        floodFill.yStart = j / COLS + 1;
                    } else if (selected == FloodFill.END) {
                        floodFill.set(floodFill.xEnd - 1, floodFill.yEnd - 1, FloodFill.FREE);
                        floodFill.xEnd = j % COLS + 1;
                        floodFill.yEnd = j / COLS + 1;
                    }
                    floodFill.set(j % COLS, j / COLS, selected);
                    redraw();
                }
            });
            frame.add(button);
        }
        this.add(frame);
    }

    private void Dock() {
        JPanel frame = new JPanel();
        JRadioButton option1 = new JRadioButton("WALL");
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = FloodFill.WALL;
            }
        });
        JRadioButton option2 = new JRadioButton("FREE");
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = FloodFill.FREE;
            }
        });
        JRadioButton option3 = new JRadioButton("START");
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = FloodFill.START;
            }
        });
        JRadioButton option4 = new JRadioButton("END");
        option4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = FloodFill.END;
            }
        });
        // Add the buttons to a button group
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);
        // Add the buttons to the frame
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        // Select the first button by default
        option1.setSelected(true);
        this.add(frame);
    }

    private void redraw() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                buts[j][i].setText(conv(floodFill.get(i, j)));
            }
        }
    }

    public FloodFillVisualizer() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Grid();
        Dock();
        JButton button = new JButton("Find Shortest Path");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floodFill.display();
                redraw();
                String path = floodFill.shortest();
                int x = floodFill.getX(), y = floodFill.getY();
                for (int i = 0; i < path.length() - 1; i++) {
                    switch (path.charAt(i)) {
                        case 'd' -> y++;
                        case 't' -> y--;
                        case 'r' -> x++;
                        case 'l' -> x--;
                    }
                    buts[y - 1][x - 1].setText("o");
                }
            }
        });
        this.add(button);

        this.pack();
        this.setVisible(true);
    }
}
