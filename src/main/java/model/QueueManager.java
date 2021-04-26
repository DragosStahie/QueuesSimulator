package model;

import controller.SimulationController;
import view.SimulationDisplay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class QueueManager implements Runnable{

    private int currentTime = 0;
    private int finalSimulationTime = 0;
    private int numberOfQueues = 0;
    private int numberOfClients = 0;

    private int minArrivalTime = 0;
    private int maxArrivalTime = 0;

    private int minServiceTime = 0;
    private int maxServiceTime = 0;

    private int totalServiceTime = 0;
    private int peakHour         = 0;

    private QueueScheduler      scheduler;
    private SimulationDisplay simulationDisplay;
    private List<Client>        generatedClients;

    private File            outputTxtFile;
    private FileWriter      fileWriter;

    public QueueManager(SimulationDisplay dsp, int clients, int queues, int minArr,
                        int maxArr, int minSer, int maxSer, int sim) {

        numberOfClients     = clients;
        numberOfQueues      = queues;
        minArrivalTime      = minArr;
        maxArrivalTime      = maxArr;
        minServiceTime      = minSer;
        maxServiceTime      = maxSer;
        finalSimulationTime = sim;

        //initialize scheduler
        scheduler = new QueueScheduler(numberOfQueues, numberOfClients);

        //initialize frame to display simulation
        simulationDisplay = dsp;

        //generate clients
        generatedClients = generateClients(numberOfClients);

        outputTxtFile = new File("eventsLog.txt");

        try {

            outputTxtFile.createNewFile();
            fileWriter = new FileWriter(outputTxtFile);
        }
        catch(IOException exception) {

            exception.printStackTrace();
        }
    }

    private ArrayList<Client> generateClients(int numberOfClients) {

        ArrayList<Client> clients = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {

            int arr = (int) ((Math.random() * 100) % maxArrivalTime);
            int ser = (int) ((Math.random() * 100) % maxServiceTime);

            while (arr < minArrivalTime || ser < minServiceTime) {

                arr = (int) ((Math.random() * 100) % maxArrivalTime);
                ser = (int) ((Math.random() * 100) % maxServiceTime);
            }

            //System.out.println("Arr and ser are: " + arr + " " + ser);
            clients.add(new Client(i, arr, ser));
        }

        Collections.sort(clients, new SortByArrivalTime());

        return clients;
    }

    static class SortByArrivalTime implements Comparator<Client> {

        @Override
        public int compare(Client c1, Client c2) {

            return (c1.getTimeOfArrival() - c2.getTimeOfArrival());
        }
    }

    @Override
    public void run() {

        currentTime = 1;
        int maxClientsPerHour = 0;
        int clientsPerHour = 0;
        try {

            while (currentTime <= finalSimulationTime) {

                System.out.println("Time: " + currentTime);
                fileWriter.write("Time: " + currentTime + '\n');

                //pick clients that arrive at this time
                //send clients to queue
                clientsPerHour = 0;

                while (!generatedClients.isEmpty()) {

                    if (generatedClients.get(0).getTimeOfArrival() == currentTime) {

                        scheduler.clientToQueue(generatedClients.get(0));
                        totalServiceTime += generatedClients.get(0).getTimeOfService();
                        clientsPerHour++;
                        generatedClients.remove(0);
                    } else {

                        break;
                    }
                }
                //update UI

                if(maxClientsPerHour < clientsPerHour) {

                    maxClientsPerHour = clientsPerHour;
                    peakHour = currentTime;
                }

                if (!generatedClients.isEmpty()) {

                    System.out.print("Waiting clients: ");
                    fileWriter.write("Waiting clients: ");

                    for (Client client : generatedClients) {

                        System.out.print(client + "; ");
                        fileWriter.write(client + "; ");

                    }
                    System.out.println();
                    fileWriter.write('\n');
                }

                fileWriter.write(scheduler.printQueues());

                System.out.println();
                fileWriter.write('\n');

                currentTime++;
                //wait for one second

                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    System.out.println("Thread has been interrupted");
                }

                if(generatedClients.isEmpty() && scheduler.closeShop()) {

                    break;
                }
            }

            scheduler.closeQueues();

            System.out.println();
            System.out.println("==========SIMULATION FINISHED==========");
            System.out.println("Average waiting time is: " + scheduler.averageWaitingTime());
            System.out.println("Average service time is: " + (float)totalServiceTime / numberOfClients);
            System.out.println("Peak hour is: " + peakHour);

            fileWriter.write('\n');
            fileWriter.write("======================SIMULATION FINISHED======================\n");
            fileWriter.write("Average waiting time is: " + scheduler.averageWaitingTime() + '\n');
            fileWriter.write("Average service time is: " + (float)totalServiceTime / numberOfClients + '\n');
            fileWriter.write("Peak hour is: " + peakHour + '\n');

            fileWriter.close();

        } catch (IOException exception) {

            exception.printStackTrace();
        }
    }

    public int getFinalSimulationTime() {

        return finalSimulationTime;
    }

    public int getNumberOfQueues() {

        return  numberOfQueues;
    }

    public int getWaitingClients() {

        return generatedClients.size();
    }

    public List<ClientQueue> getQueues() {

        return scheduler.getQueues();
    }

    public int getCurrentTime() {

        return currentTime;
    }

    public static void main(String[] args) {

        SimulationDisplay       display     = new SimulationDisplay();
        display.setVisible(true);
        SimulationController controller  = new SimulationController(display);
    }
}