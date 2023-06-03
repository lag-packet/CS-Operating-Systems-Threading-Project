import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Truck implements Runnable{

    private String truckType;
    //private int itemAmount;

    private AtomicInteger deliveriesMade;
    private AtomicBoolean isDone;
    private AtomicInteger itemAmount;
    private AtomicBoolean atManufacturer;
    private AtomicBoolean waitingForReport;
    private AtomicInteger amountDelivered;

    private Random random;
    private AssemblyLine assemblyLine;
    private Manufacturer manufacturer;

    public Truck(String truckType, AssemblyLine assemblyLine) {
        this.truckType = truckType;
        this.assemblyLine = assemblyLine;

        this.deliveriesMade = new AtomicInteger(0);
        this.isDone = new AtomicBoolean(false);
        this.itemAmount = new AtomicInteger(0);
        this.atManufacturer = new AtomicBoolean(true);
        this.waitingForReport =  new AtomicBoolean(false);
        this.amountDelivered = new AtomicInteger(0);

        this.random = new Random();

    }

    public void attachManufacturer(Manufacturer m) {
        manufacturer = m;
    }

    public AtomicBoolean isDone() {
        return isDone;
    }

    public AtomicBoolean atManufacturer () {
        return atManufacturer;
    }

    public String getTruckType() {
        return truckType;
    }

    public String getName() {
        return truckType + " Truck";
    }

    public AtomicInteger getItemAmount() {
        return itemAmount;
    }

    public AtomicInteger amountDelivered() {
        return amountDelivered;
    }

    public AtomicInteger deliveriesMade() {
        return deliveriesMade;
    }

    public void receiveReport() {
        waitingForReport.set(false);
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+Thread.currentThread().getName()
                +": "+m);
    }

    @Override
    public void run() {
       while (!isDone().get() && Main.numDeliveries != deliveriesMade.get()) {
           // Wait for loading parts
           while (itemAmount.get() == 0 && manufacturer.isLoading().get()) {
               // BW
           }
           atManufacturer.set(false);
           if (itemAmount.get() == 0) {
               //System.out.println("ITEM ZERO");
               continue;
           }
           amountDelivered.getAndAdd(itemAmount.get());
           msg(getName() + " has LOADED: " + itemAmount.get() + " items");

           // Adjust priority during delivery, wait in assembly
           Thread currentThread = Thread.currentThread();
           int originalPriority = currentThread.getPriority();
           currentThread.setPriority(originalPriority + 1);
           System.out.printf("[TRUCK] %s truck Priority increased from %d to %d\n", truckType, originalPriority,
                   currentThread.getPriority());
           msg(truckType + " truck speeds up.");
           try {
               Thread.sleep(1000); // simulate speed up
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           currentThread.setPriority(originalPriority);

           msg("Truck at assembly line...");
           assemblyLine.addTruckToQueue(this);

           // Busy wait Assembly Line report
           waitingForReport.getAndSet(true);
           while(waitingForReport.get()) {}

           // Return for a new load
           itemAmount.set(0);
           msg("Truck done at assembly line. Returning to Manufacturer.");

           // Take a break and get gas after delivery
           msg("Truck getting gas...");
           Thread.yield();
           try {
               Thread.sleep(random.nextInt(1000)); // simulate getting gas
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           deliveriesMade.getAndIncrement();
           msg("--DELIVERY FINISHED-- total deliveries: " + deliveriesMade.get() + " total parts: "
                   + amountDelivered.get());

           atManufacturer.getAndSet(true);
       }
    }
}
