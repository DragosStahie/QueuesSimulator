package model;

import java.util.ArrayList;
import java.util.List;

public class QueueScheduler {

    private List<ClientQueue>   queues;
    private int                 noQueues;
    private int                 maxNoClients;
    private static int          totalWaitingTime;

    public QueueScheduler(int queues, int clients) {

        noQueues = queues;
        maxNoClients = clients;
        totalWaitingTime = 0;

        this.queues = new ArrayList<ClientQueue>();

        for(int i = 0; i < noQueues; i++) {

            this.queues.add(new ClientQueue(30, "Queue" + (i+1)));
            this.queues.get(i).start();
        }
    }

    public void clientToQueue(Client client){

        int minWaitTime = 500;
        for(ClientQueue queue : queues) {

            if(queue.getWaitingPeriod() < minWaitTime) {

                minWaitTime = queue.getWaitingPeriod();
            }
        }

        for(ClientQueue queue : queues) {

            if(queue.getWaitingPeriod() == minWaitTime) {

                queue.addClient(client);
                break;
            }
        }
    }
    public void closeQueues() {

        for(ClientQueue queue : queues) {

            queue.setRunning(false);
        }
    }

    public static void addTotalWaitingTime(int value) {

        totalWaitingTime += value;
    }

    public float averageWaitingTime() {

        if(maxNoClients != 0) {

            return (float) totalWaitingTime / maxNoClients;
        }

        return -1;
    }

    public String printQueues() {

        String msg = "";

        for(ClientQueue queue : queues) {

            System.out.println(queue);

            msg += queue.toString() + '\n';
        }

        return msg;
    }

    public List<ClientQueue> getQueues() {

        return queues;
    }

    public boolean closeShop() {

        for(ClientQueue queue : queues) {

            if(queue.getNumberOfClients() > 0) {

                return false;
            }
        }

        return true;
    }
}
