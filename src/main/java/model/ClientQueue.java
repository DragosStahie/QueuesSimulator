package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientQueue implements Runnable{

    private BlockingQueue<Client>   clients;
    private int                     waitingPeriod;
    private String                  threadName;
    private Thread                  queueThread;
    private boolean                 running = false;

    public ClientQueue(int noClients, String name) {

        //initialize queue and waiting period
        waitingPeriod = 0;
        clients = new ArrayBlockingQueue<Client>(noClients);
        threadName = name;
        running = true;
    }

    public void addClient(Client newClient){

        //add client to queue
        //increment the waitingPeriod

        try {
            clients.add(newClient);
            waitingPeriod += newClient.getTimeOfService();
            QueueScheduler.addTotalWaitingTime(waitingPeriod);
        } catch(IllegalStateException exception){};

    }

    public void start() {

        if(queueThread == null) {

            queueThread = new Thread(this, threadName);
            queueThread.start();
        }
    }

    @Override
    public void run() {

        while (running) {

            //take the next client from queue
            //stop the thread for a time equal with the clients's serving time
            //decrement the waitingPeriod


            if (!clients.isEmpty()) {

                //System.out.print(threadName + ": ");

                for (Client client : clients) {

                    //System.out.println(client + " ");
                }

                try {

                    Thread.sleep(1000 * clients.element().getTimeOfService());
                } catch (InterruptedException e) {

                    System.out.println("Thread has been interrupted");
                }

                waitingPeriod -= clients.element().getTimeOfService();

                clients.remove();
            }
        }
    }

    public int getWaitingPeriod() {

        return waitingPeriod;
    }

    public void setRunning(boolean value) {

        running = value;
    }

    @Override
    public String toString() {

        String msg = threadName + ": ";

        if(clients.isEmpty()) {

            msg += "closed";
        }
        else {

            for (Client client : clients) {

                msg += client + "; ";
            }
        }

        return msg;
    }

    public int getNumberOfClients() {

        return clients.size();
    }
}
