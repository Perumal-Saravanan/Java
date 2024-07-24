import java.util.Scanner;

class Rocket {
    private String stage;
    private double fuel;
    private double altitude;
    private double speed;

    public Rocket() {
        this.stage = "Pre-Launch";
        this.fuel = 100.0;
        this.altitude = 0.0;
        this.speed = 0.0;
    }

    public String getStage() {
        return stage;
    }

    public double getFuel() {
        return fuel;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void startChecks() {
        System.out.println("All systems are 'Go' for launch.");
    }

    public void launch() {
        stage = "Stage 1";
    }

    public void update(int seconds) {
    for (int i = 0; i < seconds; i++) {
        if (fuel <= 0) {
            System.out.println("Mission Failed due to insufficient fuel.");
            stage = "Mission Failed";
            return;
        }
        if ("Stage 1".equals(stage)) {
            fuel -= 1.0;
            altitude += 10.0;
            speed += 1000.0;
            System.out.println("Stage: 1, Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
            if (fuel <= 50.0) {
                stage = "Stage 2";
                System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
            }
        } else if ("Stage 2".equals(stage)) {
            fuel -= 0.5;
            altitude += 5.0;
            speed += 500.0;
            System.out.println("Stage: 2, Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
            if (altitude >= 100.0) {
                stage = "Orbit Achieved";
                System.out.println("Orbit achieved! Mission Successful.");
                return;
            }
        }
    }
}
}

class RocketLaunchSimulator {
    private Rocket rocket;
    private boolean preLaunchChecksCompleted;

    public RocketLaunchSimulator() {
        this.rocket = new Rocket();
        this.preLaunchChecksCompleted = false;
    }

    public void startChecks() {
        rocket.startChecks();
        preLaunchChecksCompleted = true;
    }

    public void launch() {
        if (preLaunchChecksCompleted) {
            rocket.launch();
        } else {
            System.out.println("Pre-launch checks have not been completed.");
        }
    }

    public void fastForward(int seconds) {
        rocket.update(seconds);
    }

    public void simulate() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            switch (parts[0]) {
                case "start_checks":
                    startChecks();
                    break;
                case "launch":
                    launch();
                    break;
                case "fast_forward":
                    if (parts.length > 1) {
                        int seconds = Integer.parseInt(parts[1]);
                        fastForward(seconds);
                    } else {
                        System.out.println("Please specify the number of seconds to fast forward.");
                    }
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

public class Main {
    public static void main(String[] args) {
        RocketLaunchSimulator simulator = new RocketLaunchSimulator();
        simulator.simulate();
    }
}
