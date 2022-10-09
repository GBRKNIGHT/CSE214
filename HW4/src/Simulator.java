
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

import java.util.*;

public class Simulator {
    /*
    All required data fields.
     */
    private Router dispatcher = new Router();
    private LinkedList<Router> routers = new LinkedList<>();
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
    private static int testRunTime = 0;
    private Router waitlist = new Router();

    public static final int MAX_PACKETS = 3;


    /*
    Default constructor.
     */
    public Simulator(){
    }

    /*
    Below are setters for user input.
     */
    public void setNumIntRouters(int numIntRouters) {
        this.numIntRouters = numIntRouters;
    }

    public void setArrivalProb(double arrivalProb) {
        this.arrivalProb = arrivalProb;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public void setMaxPacketSize(int maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
    }

    public void setMinPacketSize(int minPacketSize) {
        this.minPacketSize = minPacketSize;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Simulate method, which is the most important part to run the simulation.
     * @return average dealing time for each arrived packets.
     * @throws IntermediateRouterAllFullException Self-defined exception, indicate all routers are full
     */
    public double simulate() throws IntermediateRouterAllFullException {
        this.totalServiceTime = 0;
        this.totalPacketsArrived = 0;
        this.packetsDropped = 0;
        for(int i = 0; i < numIntRouters; i++){
            Router temp = new Router();
            temp.setMaxBufferSize(maxBufferSize);
            routers.add(temp);
        }
        while (true){
            try {
            if (++Simulator.testRunTime > this.duration){
                break;
            }
            //Simulator.testRunTime++;
            System.out.println("Time: " + Simulator.testRunTime);
            for (int i = 0; i < MAX_PACKETS; i++){
                if (Math.random()< arrivalProb){
                    /*
                    Parts to generate random packet.
                     */
                    Packet temp = new Packet();
                    temp.setId(Packet.getPacketCount());
                    temp.setPacketSize(randInt(maxPacketSize,minPacketSize));
                    temp.setTimeArrive(Simulator.testRunTime);
                    temp.setTimeToDest(Simulator.testRunTime+ temp.getPacketSize()/100);
                    dispatcher.add(temp);
                    System.out.println("Packet "+ temp.getId() +
                            " arrives at dispatcher with size " + temp.getPacketSize()+". ");
                    int sendTo = Router.sendPacketTo(routers);
                    System.out.println("Packet "+temp.getId()+" sent to Router " + (sendTo+1) +".");
                    dispatcher.dequeue();
                    routers.get(sendTo).enqueue(temp);
                }
            }
            for (int i = 0; i < routers.size(); i++){
                String s = "R";
                s += (i+1);
                s += ": ";
                s += routers.get(i).toString();
                System.out.println(s);
            }

            for (int i = 0; i < routers.size(); i++){
                Packet p = routers.get(i).peek();
                if (p != null && p.getTimeToDest()!= 0){
                    p.setTimeToDest(p.getTimeToDest()-1);
                    if (p.getTimeToDest() == 0){
                        waitlist.enqueue(routers.get(i).peek());
                    }
                }
            }
            for (int i = 0; i < bandwidth; i++){
                for (int j = 0; j < routers.size(); j++){
                    if (waitlist.size() > 0 && waitlist.peek().equals(routers.get(j).peek())){
                        totalServiceTime += waitlist.peek().getProcessingTime();
                        totalServiceTime += waitlist.peek().getProcessingTime();
                        System.out.println("Packet " + waitlist.peek().getId() +
                                " has successfully reached its destination.  +" + waitlist.peek().getPacketSize()/100);
                        totalPacketsArrived++;
                        routers.get(j).dequeue();
                        waitlist.dequeue();
                    }
                }
            }
            System.out.println("--------------------------------------------------------------------------------------");
        }
            catch (IntermediateRouterAllFullException IRAFE){
                System.out.println("Network is congested. Packet "+ Packet.getPacketCount() + " is dropped.");
                this.packetsDropped++;
            }
            catch (ArithmeticException AE){
            }
        }

        return totalServiceTime/totalPacketsArrived;

    }


    /**
     * Method to find a random integer in a specific given range.
     * @param maxVal max value
     * @param minVal min value
     * @return the randomly generated integer.
     * @throws InputMismatchException which will occur when max < min.
     */

    private int randInt(int maxVal, int minVal) throws InputMismatchException{
        if (maxVal < minVal){
            System.out.println("The maxval cannot less than minval!");
            throw new InputMismatchException();
        }
        int interval = maxVal - minVal;
        int result = (int) (Math.random()*interval + minVal);
        return result;
    }

    /**
     * Main method to run the program.
     * @param args
     * @throws IntermediateRouterAllFullException Self-defined exception, indicate all routers are full
     */
    public static void main(String[] args) throws IntermediateRouterAllFullException {
        try{
            while (true){
                /*
                Scanner for user input.
                 */
                Scanner stdin = new Scanner(System.in);
                System.out.println("Starting simulator...");
                Simulator simulator = new Simulator();
                System.out.println("Enter the number of Intermediate routers: ");
                String userInputNumInter = stdin.nextLine().trim();
                simulator.setNumIntRouters(Integer.parseInt(userInputNumInter));
                System.out.println("Enter the arrival probability of a packet: ");
                String userInputArriProb = stdin.nextLine().trim();
                simulator.setArrivalProb(Double.parseDouble(userInputArriProb));
                System.out.println("Enter the maximum buffer size of a router: ");
                String userInputMaxBuffer = stdin.nextLine().trim();
                simulator.setMaxBufferSize(Integer.parseInt(userInputMaxBuffer));
                System.out.println("Enter the minimum size of a packet: ");
                String userInputMinPacket = stdin.nextLine().trim();
                simulator.setMinPacketSize(Integer.parseInt(userInputMinPacket));
                System.out.println("Enter the maximum size of a packet: ");
                String userInputMaxPacket = stdin.nextLine().trim();
                simulator.setMaxPacketSize(Integer.parseInt(userInputMaxPacket));
                System.out.println("Enter the bandwidth size: ");
                String userInputBind = stdin.nextLine().trim();
                simulator.setBandwidth(Integer.parseInt(userInputBind));
                System.out.println("Enter the simulation duration: ");
                String userInputDuration = stdin.nextLine().trim();
                simulator.setDuration(Integer.parseInt(userInputDuration));
                /*
                Exception will be thrown if the input is wrong.
                 */
                if (simulator.numIntRouters <= 0 || simulator.arrivalProb< 0|| simulator.arrivalProb>1||
                        simulator.minPacketSize< 0|| simulator.maxPacketSize<0 ||
                        simulator.maxPacketSize< simulator.minPacketSize||simulator.maxBufferSize<0){
                    throw new InputMismatchException();
                }
                simulator.simulate();
                /*
                Printout statements for the result output.
                 */
                System.out.println();
                System.out.println("===================================================================================");
                System.out.println("Simulation ending...");
                System.out.println("Total service time: " + Simulator.testRunTime);
                System.out.println("Total packets served: "+ simulator.totalPacketsArrived);
                System.out.println("Average service time per packet: " +
                        ((double)Simulator.testRunTime/(double)simulator.totalPacketsArrived));
                System.out.println("Total packets dropped:" + simulator.packetsDropped);
                System.out.println("===================================================================================");
                System.out.println("Do you want to try another simulation? y/n");
                String again = stdin.nextLine().trim().toLowerCase();
                if (again.equals("y")){}
                else if (again.equals("n")){
                    System.out.println("Program terminating successfully...");
                    System.exit(0);
        }
//            /*
//            Pre-written test cases.
//             */
//            simulator.setNumIntRouters(4);
//            simulator.setArrivalProb(0.5);
//            simulator.setMaxBufferSize(10);
//            simulator.setMinPacketSize(500);
//            simulator.setMaxPacketSize(1500);
//            simulator.setBandwidth(2);
//            simulator.setDuration(25);
            }
        }catch (InputMismatchException IME ){
            System.out.println("Please enter numbers correctly!");
        }catch (NumberFormatException NFE){
            System.out.println("Please enter numbers correctly!");
        }catch (ArithmeticException AE){
            System.out.println("Error occurred! \n Please re-try.");
        }
    }

}
