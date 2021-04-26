package view;

import model.QueueManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SimulationAnimation extends JFrame implements ActionListener{


    private ContentPanel  content = new ContentPanel();
    private boolean running = true;
    private Timer   timer;
    private int     noQueues;
    private int     width = 0;
    private int     height = 0;

    private QueueManager manager;

    public SimulationAnimation (QueueManager manager) {

        this.manager = manager;

        noQueues = manager.getNumberOfQueues();
        //setting the layout of the graphical interface

        width = Math.min(manager.getWaitingClients() * 35,1200);
        width = Math.max(width, 800);
        height = Math.min(noQueues * 35, 700);
        height = Math.max(height, 400);
        content.setPreferredSize(new Dimension(width, height));

        this.setTitle("Live Queue Simulation");
        this.setContentPane(content);
        this.pack();
        this.setLocationRelativeTo(null);

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        content.repaint();
    }

    class ContentPanel extends JPanel {

        ContentPanel() {

            super();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            g.setColor(new Color(50, 15, 61));
            g.fillRect(0, 0, width, height);

            g.setColor(new Color(37, 167, 179));
            for(int i = 0; i < noQueues; i++) {

                g.fillRect(10, 50 + i * 30, 20, 20);
            }

            g.setColor(new Color(189, 15, 15));
            for(int i = 0; i < manager.getWaitingClients(); i++) {

                g.fillOval(10 + i * 20, 10, 15, 15);
            }

            g.setColor(new Color(18, 196, 12));
            for(int i = 0; i < noQueues; i++) {

                if(manager.getQueues().get(i).getNumberOfClients() == 0) {

                    g.setFont(new Font("Verdana", Font.BOLD, 16));
                    g.drawString("Closed", 35, 67 + i * 30);
                }
                else {
                    for (int j = 0; j < manager.getQueues().get(i).getNumberOfClients(); j++) {

                        g.fillOval(35 + j * 20, 55 + i * 30, 15, 15);
                    }
                }
            }

            g.setFont(new Font("Verdana", Font.BOLD, 16));
            g.drawString("Simulation time: " + manager.getCurrentTime(), width - 180, height - 15);
        }
    }
}
