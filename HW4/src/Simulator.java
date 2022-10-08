import java.util.*;

public class Simulator {
    /*
    All required data fields.
     */
    private Router dispatcher = new Router();
    private ArrayList<Router> routers = new ArrayList<>();
    private int totalServiceTime;
    private int totalPacketsArrived;
    private int packetsDropped;
    private double arrivalProb;
    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandwidth;
    private int duration;

    public static final int MAX_PACKETS = 3;


    /*
    Default constructor.
     */
    public Simulator(){

    }


    public double simulate(double arrivalProb, int numIntRouters, int maxBufferSize, int minPacketSize,
                           int maxPacketSize, int bandwidth, int duration){
        int runningTime = 0;
        this.totalServiceTime = 0;
        this.totalPacketsArrived = 0;
        this.packetsDropped = 0;
        this.numIntRouters = numIntRouters;
        this.maxBufferSize = maxBufferSize;
        this.minPacketSize = minPacketSize;
        this.maxPacketSize = maxPacketSize;
        this.arrivalProb = arrivalProb;
        this.bandwidth = bandwidth;
        for(int i = 0; i < numIntRouters; i++){
            Router temp = new Router();
            routers.add(temp);
        }
        for (int i = 0; i < MAX_PACKETS; i++){
            if (Math.random()< arrivalProb){
                Packet temp = new Packet();
                temp.setTimeArrive(runningTime);
                temp.setPacketSize((int)(Math.random()*(maxPacketSize-minPacketSize)+minPacketSize));

            }
        }


        return 0;
    }



}
