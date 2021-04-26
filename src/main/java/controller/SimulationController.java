package controller;

import model.QueueManager;
import view.SimulationAnimation;
import view.SimulationDisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {

    private SimulationDisplay display;

    public SimulationController (SimulationDisplay dsp) {

        display = dsp;

        //adding the listeners to the buttons
        dsp.addRunListener(new RunListener());

    }

    //implementing the action done by each listener for the buttons
    class RunListener implements ActionListener {

        @Override
        public void actionPerformed (ActionEvent e) {

            display.clearError();
            int noClients, noQueues, simulationTime, minArrTime, maxArrTime, minSerTime, maxSerTime;

            noClients       = display.getNoClients();
            noQueues        = display.getNoQueues();
            simulationTime  = display.getSimulationTime();
            minArrTime      = display.getMinArrTime();
            maxArrTime      = display.getMaxArrTime();
            minSerTime      = display.getMinSerTime();
            maxSerTime      = display.getMaxSerTime();

            if(minArrTime >= maxArrTime) {

                display.showError("Maximum arrival time cannot be less than minimum arrival time!");
                return;
            }

            if(minArrTime > simulationTime / 2) {

                display.showError("Minimum arrival time cannot be grater than half of the simulation time!");
                return;
            }


            if(maxArrTime > simulationTime) {

                display.showError("Maximum arrival time cannot be higher than simulation time!");
                return;
            }

            if(minSerTime >= maxSerTime) {

                display.showError("Maximum serving time cannot be less than minimum serving time!");
                return;
            }

            QueueManager gen = new QueueManager(display, noClients, noQueues, minArrTime, maxArrTime,
                                                            minSerTime, maxSerTime, simulationTime);
            Thread manager = new Thread(gen);
            manager.start();

            SimulationAnimation animation = new SimulationAnimation(gen);
            animation.setVisible(true);
        }
    }
}

