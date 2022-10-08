
import java.util.*;


public class Router extends LinkedList<Packet> {


    private int maxBufferSize;

    private LinkedList<Packet> router;

    public LinkedList<Packet> getRouter() {
        return this.router;
    }

    public Router(){
        this.router = new LinkedList<>();
    }


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
        Packet dequeuedPacket = this.router.peek().clone();
        this.router.remove();
        return dequeuedPacket;
    }


    /*
    isEmpty method.
     */
    @Override
    public boolean isEmpty(){
        return this.router.isEmpty();
    }


    public String toString(){
        String s = "{";
        for (int i = 0; i < router.size(); i++){
            s += router.get(i+1).toString() + " , ";
        }
        s+= "}";
        return s;
    }

    /*
    Send packet to method.
     */
    public static int sendPacketTo(ArrayList<Router> routers)throws IntermediateRouterAllFullException{
        ArrayList<Integer> routersSize = new ArrayList<>();
        for (int i = 0; i < routers.size(); i++){
            Router temp = (Router) routers.get(i);
            routersSize.add(temp.size());
            }
        if (Collections.max(routersSize) <= 0 ) {
            throw new IntermediateRouterAllFullException();
        }
        return Collections.max(routersSize);
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }
}


class IntermediateRouterAllFullException extends Exception{
    public IntermediateRouterAllFullException(){
        System.out.println("All intermediate routers are currently full!");
    }
}

