import java.util.Scanner;

class Satellite {
    private String orientation;
    private String solarPanels;
    private int dataCollected;

    public Satellite() {
        this.orientation = "North";
        this.solarPanels = "Inactive";
        this.dataCollected = 0;
    }

    public void rotate(String direction) {
        if (direction.equals("North") || direction.equals("South") || direction.equals("East") || direction.equals("West")) {
            this.orientation = direction;
            System.out.println("Satellite rotated to " + direction + ".");
        } else {
            System.out.println("Invalid direction. Use North, South, East, or West.");
        }
    }

    public void activatePanels() {
        this.solarPanels = "Active";
        System.out.println("Solar panels activated.");
    }

    public void deactivatePanels() {
        this.solarPanels = "Inactive";
        System.out.println("Solar panels deactivated.");
    }

    public void collectData() {
        if (this.solarPanels.equals("Active")) {
            this.dataCollected += 10;
            System.out.println("Data collected. Total data: " + this.dataCollected + " units.");
        } else {
            System.out.println("Solar panels are inactive. Cannot collect data.");
        }
    }

    public void displayStatus() {
        System.out.println("Current Status:");
        System.out.println("Orientation: " + this.orientation);
        System.out.println("Solar Panels: " + this.solarPanels);
        System.out.println("Data Collected: " + this.dataCollected + " units");
    }
}

public class SatelliteCommandSystem {
    public static void main(String[] args) {
        Satellite satellite = new Satellite();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");

            switch (parts[0]) {
                case "rotate":
                    if (parts.length > 1) {
                        satellite.rotate(parts[1]);
                    } else {
                        System.out.println("Please specify a direction to rotate.");
                    }
                    break;
                case "activatePanels":
                    satellite.activatePanels();
                    break;
                case "deactivatePanels":
                    satellite.deactivatePanels();
                    break;
                case "collectData":
                    satellite.collectData();
                    break;
                case "status":
                    satellite.displayStatus();
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
