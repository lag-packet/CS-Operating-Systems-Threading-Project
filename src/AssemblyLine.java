import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class AssemblyLine implements Runnable {

    private Vector<Truck> deliveryQueue;
    private Vector<Integer> parts;
    private AtomicInteger completedBunnies;

    public AssemblyLine() {
        this.deliveryQueue = new Vector<>();
        this.parts = new Vector<>(4);
        for (int i = 0; i < 4; i++) {
            parts.add(0);
        }
        completedBunnies = new AtomicInteger(0);
    }

    public void addTruckToQueue(Truck truck) {
        deliveryQueue.addElement(truck);
    }

    public AtomicInteger getCompletedBunnies() {
        return completedBunnies;
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+Thread.currentThread().getName()
                +": "+m);
    }

    @Override
    public void run() {
        while (true) {
            if (!deliveryQueue.isEmpty()) {
                Truck truck = deliveryQueue.firstElement();
                msg("Truck delivery queue update " + truck.getName());

                try {
                    Thread.sleep(5000); // simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int deliveredParts = truck.getItemAmount().get();
                parts.set(0, parts.get(0) + deliveredParts);

                // Assemble bunnies
                while (parts.get(0) >= 4 && parts.get(1) >= 1 && parts.get(2) >= 1 && parts.get(3) >= 1) {
                    completedBunnies.getAndIncrement();
                    msg("Bunny Assembled.");

                    parts.set(0, parts.get(0) - 4);
                    parts.set(1, parts.get(1) - 1);
                    parts.set(2, parts.get(2) - 1);
                    parts.set(3, parts.get(3) - 1);
                }

                deliveryQueue.removeElementAt(0);

                msg("Trucked received report.");
                truck.receiveReport();
            }
        }
    }
}
