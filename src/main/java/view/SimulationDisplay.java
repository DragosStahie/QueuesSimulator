package view;

import model.QueueManager;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SimulationDisplay extends JFrame{

    private JPanel		panel1			= new JPanel();
    private JPanel		panel2			= new JPanel();
    private JPanel		panel3			= new JPanel();
    private JPanel		panel4			= new JPanel();
    private JPanel              panel5                  = new JPanel();
    private JPanel		buttonPanel		= new JPanel();
    private JPanel		content			= new JPanel();
    private JLabel		clientsTag		= new JLabel("Number of clients:");
    private JLabel		queuesTag		= new JLabel("Number of queues:");
    private JLabel		minArrTag		= new JLabel("Minimum arrival time:");
    private JLabel		maxArrTag		= new JLabel("Maximum arrival time:");
    private JLabel		minSerTag		= new JLabel("Minimum service time:");
    private JLabel		maxSerTag		= new JLabel("Maximum service time:");
    private JLabel		maxSimTag		= new JLabel("Maximum simulation time:");
    private JSpinner    noClients		= new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
    private JSpinner	noQueues		= new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private JSpinner	minArrTime		= new JSpinner(new SpinnerNumberModel(1, 1, 300, 1));
    private JSpinner	maxArrTime		= new JSpinner(new SpinnerNumberModel(1, 1, 700, 1));
    private JSpinner	minSerTime		= new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
    private JSpinner	maxSerTime		= new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
    private JSpinner	simulationTime	= new JSpinner(new SpinnerNumberModel(10, 10, 1500, 10));
    private JButton		runButton		= new JButton("Run Simulation!");
    private JLabel 		errorMsg		= new JLabel("Input Error!");


    private QueueManager manager;

    public SimulationDisplay () {

        //setting the layout of the graphical interface
        errorMsg.setForeground(Color.red);
        errorMsg.setFont(new Font("", Font.BOLD, 18));
        errorMsg.setVisible(false);
        panel5.add(errorMsg);
        panel5.setPreferredSize(new Dimension(600, 100));
        panel5.setBorder(new EmptyBorder(25, 10, 25, 10));

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(runButton);
        runButton.setFont(new Font("", Font.BOLD, 16));
        runButton.setPreferredSize(new Dimension(160, 30));

        panel1.setLayout(new GridLayout(1, 0, 15, 8));
        clientsTag.setFont(new Font("", Font.BOLD, 16));
        queuesTag.setFont(new Font("", Font.BOLD, 16));
        panel1.add(clientsTag);
        panel1.add(noClients);
        panel1.add(queuesTag);
        panel1.add(noQueues);

        panel2.setLayout(new GridLayout(1, 0, 15, 8));
        minArrTag.setFont(new Font("", Font.BOLD, 16));
        maxArrTag.setFont(new Font("", Font.BOLD, 16));
        panel2.add(minArrTag);
        panel2.add(minArrTime);
        panel2.add(maxArrTag);
        panel2.add(maxArrTime);

        panel3.setLayout(new GridLayout(1, 0, 15, 8));
        minSerTag.setFont(new Font("", Font.BOLD, 16));
        maxSerTag.setFont(new Font("", Font.BOLD, 16));
        panel3.add(minSerTag);
        panel3.add(minSerTime);
        panel3.add(maxSerTag);
        panel3.add(maxSerTime);

        panel4.setLayout(new GridLayout(1, 0, 15, 8));
        maxSimTag.setFont(new Font("", Font.BOLD, 16));
        panel4.add(maxSimTag);
        panel4.add(simulationTime);

        content.setLayout(new FlowLayout());
        content.setBorder(new EmptyBorder(20, 30, 20, 30));
        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        content.add(panel4);
        content.add(panel5);
        content.add(buttonPanel);


        content.setPreferredSize(new Dimension(800, 300));

        this.setTitle("Cea mai mare simulare");
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    //reading user input
    public Integer getNoClients() {

        return (Integer) noClients.getValue();
    }

    public Integer getNoQueues() {

        return (Integer) noQueues.getValue();
    }

    public Integer getMinArrTime() {

        return (Integer) minArrTime.getValue();
    }

    public Integer getMaxArrTime() {

        return (Integer) maxArrTime.getValue();
    }

    public Integer getMinSerTime() {

        return (Integer) minSerTime.getValue();
    }

    public Integer getMaxSerTime() {

        return (Integer) maxSerTime.getValue();
    }

    public Integer getSimulationTime() {

        return (Integer) simulationTime.getValue();
    }


    //methods for adding listeners to the buttons
    public void addRunListener(ActionListener listener) {

        runButton.addActionListener(listener);
    }

    //method for showing the error message
    public void showError(String msg) {

        errorMsg.setText("<html>" + msg + "</html>");
        errorMsg.setVisible(true);
    }

    public void clearError() {

        errorMsg.setVisible(false);
    }

}
