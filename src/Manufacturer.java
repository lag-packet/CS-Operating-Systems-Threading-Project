import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Manufacturer implements Runnable {

    private String manufacturerType = "";
    private AtomicInteger itemsManufactured;
    private AtomicBoolean isLoading;

    private Random random;
    private Truck truck;

    public Manufacturer(String manufacturerType, Truck truck) {
        this.manufacturerType = manufacturerType;
        this.truck = truck;

        this.random = new Random();
        this.isLoading = new AtomicBoolean(true);

        itemsManufactured = new AtomicInteger(0);

        // attacher
        truck.attachManufacturer(this);


    }


    public String getName() {
        return manufacturerType + " Manufacturer";
    }

    public AtomicBoolean isLoading() {
        return isLoading;
    }


    @Override
    public void run() {
        while (!truck.isDone().get() && truck.deliveriesMade().get() != Main.numDeliveries) {
            // Produce Items
            try {
                Thread.sleep(random.nextInt(5000)); // simulate produce items.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (manufacturerType.equalsIgnoreCase("Head")
                    || manufacturerType.equalsIgnoreCase("Torso")
                    || manufacturerType.equalsIgnoreCase("Tail")) {
                itemsManufactured.getAndSet(random.nextInt(70, 101));
            } else if (manufacturerType.equalsIgnoreCase("Leg")) {
                itemsManufactured.getAndSet(random.nextInt(250, 501));
            } else {
                System.err.println("Invalid Manufacturer.");
            }

            msg("Manufacturer produced: " + itemsManufactured.get());

            // Wait for truck
            msg("Waiting for truck...");
            while (!truck.atManufacturer().get()) {}
            isLoading.set(true);

            // Load parts
            System.out.printf("[MANUFACTURER] %s manufacturer MAKES %d items\n", manufacturerType,
                    itemsManufactured.get());
            try {
                Thread.sleep(random.nextInt(10000)); // simulate load items.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            truck.getItemAmount().set(itemsManufactured.get());
            // msg(manufacturerType + " manufacturer LOADS:" + itemsManufactured + " items");
            isLoading.set(false);
        }
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+Thread.currentThread().getName()
                +": "+m);
    }
}
