
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */


import java.util.*;


public class Router extends LinkedList<Packet> {

    /*
    required data fields.
     */
    private int maxBufferSize;
    private LinkedList<Packet> router;
    public LinkedList<Packet> getRouter() {
        return this.router;
    }

    public Router(){
        this.router = new LinkedList<>();
    }


    /*
    need to override these methods to apply java LinkedList API class.
     */
    @Override
    public Packet element(){
        return this.router.element();
    }

    @Override
    public boolean offer(Packet p){
        return  this.router.offer(p);
    }

    @Override
    public Packet peek(){
        return this.router.peek();
    }

    @Override
    public Packet poll(){
        return this.router.poll();
    }

    @Override
    public Packet remove(){
        return this.router.remove();
    }

    /*
    Size method.
     */
    @Override
    public int size(){
        return this.router.size();
    }


    /*
    enqueue method
     */
    public void enqueue(Packet p){
        this.router.add(p);
    }


    /*
    dequeue method
     */
    public Packet dequeue(){
        Packet dequeuedPacket = this.router.peek();
        if (this.router.isEmpty()==false){
            this.router.removeFirst();
        }
        return dequeuedPacket;
    }


    /*
    isEmpty method.
     */
    @Override
    public boolean isEmpty(){
        return this.router.isEmpty();
    }


    /**
     * To string method.
     * @return String in a specific given format.
     */
    public String toString(){
        String s = "{ ";
        for (int i = 0; i < router.size(); i++){
            s += router.get(i).toString() + ",";
        }
        s+= " }";
        return s;
    }

    /**
     * Send packet to method, basically, this method does not enqueue, it is just needed to point out what is the the
     * best intermediate router that this packet can use.
     * If there are multiple routers have same free spaces, it will choose the first one.
     * @param routers The LinkedList of intermediate routers
     * @return int , the best router to go to.
     * @throws IntermediateRouterAllFullException Self-defined exception, indicate all routers are full
     */
    public static int sendPacketTo(LinkedList<Router> routers)throws IntermediateRouterAllFullException{
        ArrayList<Integer> overallAvailableBufferSize = new ArrayList<>();
        int sendTo = 0;
        for (int i = 0; i < routers.size(); i++){
            overallAvailableBufferSize.add (routers.get(i).maxBufferSize - routers.get(i).size());
        }
        int maxAvailable = Collections.max(overallAvailableBufferSize);
        /*
        If all routers are full, throw exception.
         */
        if (maxAvailable <= 0){
            throw new IntermediateRouterAllFullException();
        }
        for (int i = 0; i < routers.size(); i++){
            if (overallAvailableBufferSize.get(i) == maxAvailable){
                sendTo = i;
                break;
            }
        }
        return sendTo;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public int getMaxBufferSize() {
        return this.maxBufferSize;
    }

}


class IntermediateRouterAllFullException extends Exception{
    public IntermediateRouterAllFullException(){
        System.out.println("All intermediate routers are currently full!");
    }
}

