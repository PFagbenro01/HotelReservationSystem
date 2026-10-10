import java.util.Scanner;

public class Main {
    private static final int FLOORS = 3;
    private static final int ROOMS_PER_FLOOR = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean[][] occupied = new boolean[FLOORS][ROOMS_PER_FLOOR];
        boolean running = true;

        while (running) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Display rooms");
            System.out.println("2. Reserve a room");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. Check a room");
            System.out.println("5. Count available rooms");
            System.out.println("6. Exit");

            int choice = readNumber(scanner, "Choose an option: ");
            switch (choice) {
                case 1:
                    displayRooms(occupied);
                    break;
                case 2:
                    updateReservation(scanner, occupied, true);
                    break;
                case 3:
                    updateReservation(scanner, occupied, false);
                    break;
                case 4:
                    checkRoom(scanner, occupied);
                    break;
                case 5:
                    System.out.println("Available rooms: " + countAvailable(occupied));
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Please choose an option from 1 to 6.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void displayRooms(boolean[][] occupied) {
        System.out.println("\nRoom status (A = available, O = occupied)");
        for (int floor = 0; floor < occupied.length; floor++) {
            System.out.print("Floor " + (floor + 1) + ": ");
            for (int room = 0; room < occupied[floor].length; room++) {
                System.out.print("Room " + (room + 1) + "="
                        + (occupied[floor][room] ? "O" : "A") + "  ");
            }
            System.out.println();
        }
    }

    private static void updateReservation(Scanner scanner, boolean[][] occupied, boolean reserve) {
        int[] room = readRoom(scanner, occupied);
        if (room == null) {
            return;
        }

        int floorIndex = room[0];
        int roomIndex = room[1];
        if (reserve) {
            if (occupied[floorIndex][roomIndex]) {
                System.out.println("That room is already occupied.");
            } else {
                occupied[floorIndex][roomIndex] = true;
                System.out.println("Room reserved.");
            }
        } else if (!occupied[floorIndex][roomIndex]) {
            System.out.println("That room has no reservation to cancel.");
        } else {
            occupied[floorIndex][roomIndex] = false;
            System.out.println("Reservation canceled.");
        }
    }

    private static void checkRoom(Scanner scanner, boolean[][] occupied) {
        int[] room = readRoom(scanner, occupied);
        if (room == null) {
            return;
        }

        String status = occupied[room[0]][room[1]] ? "occupied" : "available";
        System.out.println("That room is " + status + ".");
    }

    private static int[] readRoom(Scanner scanner, boolean[][] occupied) {
        int floor = readNumber(scanner, "Floor (1-" + occupied.length + "): ");
        int room = readNumber(scanner, "Room (1-" + occupied[0].length + "): ");

        if (floor < 1 || floor > occupied.length
                || room < 1 || room > occupied[0].length) {
            System.out.println("Invalid floor or room number.");
            return null;
        }

        return new int[] { floor - 1, room - 1 };
    }

    private static int countAvailable(boolean[][] occupied) {
        int count = 0;
        for (boolean[] floor : occupied) {
            for (boolean room : floor) {
                if (!room) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int readNumber(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a whole number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}

