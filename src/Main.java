import java.util.Scanner;

public class Main {

    public static int numDeliveries = 4;
    public static long time = System.currentTimeMillis();

    public static void main(String[] args) {
        System.out.println("How many deliveries: ");
        Scanner scanner = new Scanner(System.in);
        numDeliveries = scanner.nextInt();

        // Initialize Objects
        AssemblyLine assemblyLine = new AssemblyLine();

        Truck headTruck = new Truck("Head", assemblyLine);
        Truck tailTruck = new Truck("Tail", assemblyLine);
        Truck torsoTruck =  new Truck("Torso", assemblyLine);
        Truck legTruck = new Truck("Leg", assemblyLine);

        Manufacturer headManufacturer = new Manufacturer("Head", headTruck);
        Manufacturer tailManufacturer = new Manufacturer("Tail", tailTruck);
        Manufacturer torsoManufacturer = new Manufacturer("Torso",torsoTruck);
        Manufacturer legManufacturer = new Manufacturer("Leg", legTruck);

        // Initialize Threads for Objects.
        Thread assemblyLineThread = new Thread(assemblyLine);
        assemblyLineThread.setName("Assembly Line");

        Thread headTruckThread = new Thread(headTruck);
        headTruckThread.setName("Head Truck");
        Thread tailTruckThread = new Thread(tailTruck);
        tailTruckThread.setName("Tail Truck");
        Thread torsoTruckThread = new Thread(torsoTruck);
        torsoTruckThread.setName("Torso Truck");
        Thread legTruckThread = new Thread(legTruck);
        legTruckThread.setName("Leg Truck");

        Thread headManThread = new Thread(headManufacturer);
        headManThread.setName("Head Manufacturer");
        Thread tailManThread = new Thread(tailManufacturer);
        tailManThread.setName("Tail Manufacturer");
        Thread torsoManThread = new Thread(torsoManufacturer);
        torsoManThread.setName("Torso Manufacturer");
        Thread legManThread = new Thread(legManufacturer);
        legManThread.setName("Leg Manufacturer");

        // Start Threads
        headTruckThread.start();
        tailTruckThread.start();
        torsoTruckThread.start();
        legTruckThread.start();

        headManThread.start();
        tailManThread.start();
        torsoManThread.start();
        legManThread.start();

        assemblyLineThread.start();

        try {
            headManThread.join();
            tailManThread.join();
            torsoTruckThread.join();
            legTruckThread.join();
            headManThread.join();
            tailManThread.join();
            torsoManThread.join();
            legManThread.join();
            assemblyLineThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}