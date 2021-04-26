package model;

public class Client {

    private int clientID;
    private int timeOfArrival;
    private int timeOfService;

    public Client(int id, int arr, int ser) {

        this.clientID = id;
        this.timeOfArrival = arr;
        this.timeOfService = ser;
    }

    public int getClientID() {

        return clientID;
    }

    public int getTimeOfArrival() {

        return timeOfArrival;
    }

    public int getTimeOfService() {

        return timeOfService;
    }

    @Override
    public String toString(){

        return "(" + clientID + ", " + timeOfArrival + ", " + timeOfService + ")";
    }

}
