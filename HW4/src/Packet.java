public class Packet {
    private static int packetCount = 0;
    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    /*
    Default constructor
     */
    public Packet(){
        this.packetCount++;
    }

    /*
    Constructor with given data fields
     */
    public Packet (int id, int packetSize, int timeArrive, int timeToDest){
        this.packetCount++;
        this.id = id;
        this.packetSize = packetSize;
        this.timeArrive = timeArrive;
        this.timeToDest = timeToDest;
    }

    /*
    Getters and setters for all variables.
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    public static int getPacketCount() {
        return packetCount;
    }

    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    public int getTimeArrive() {
        return timeArrive;
    }

    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    public int getTimeToDest() {
        return timeToDest;
    }

    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /*
    toString method in given format.
     */
    public String toString(){
        String s = "[ " + this.getId()+" , " + this.getTimeArrive()+ " , " + this.getTimeToDest()+ " ]";
        System.out.println(s);
        return s;
    }

    public int getProcessingTime(){
        return this.getTimeToDest() - this.getTimeArrive();
    }

    public Packet clone(){
        return new Packet(this.id,this.packetSize,this.timeArrive,this.timeToDest);
    }
}
