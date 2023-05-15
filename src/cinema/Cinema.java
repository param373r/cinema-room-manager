package cinema;

import java.util.Scanner;

public class Cinema {
    private static String[][] cinemaHall;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int totalRows = scan.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scan.nextInt();

        cinemaHall = new String[totalRows + 1][seatsPerRow + 1];

        setCinemaHall(totalRows, seatsPerRow);
        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int choice = scan.nextInt();
            if (choice == 1) {
                printCinemaHall();
            } else if (choice == 2) {
                while (true) {
                    System.out.println("Enter a row number: ");
                    int rowNum = scan.nextInt();
                    System.out.println("Enter a seat number in that row: ");
                    int seatNum = scan.nextInt();
                    if (rowNum > totalRows || seatNum > seatsPerRow) {
                        System.out.println("Wrong input");
                    } else if (cinemaHall[rowNum][seatNum].equals("S")) {
                        int ticketPrice = calculateTicketPrice(rowNum, seatNum);
                        System.out.printf("Ticket price: $%d\n\n", ticketPrice);
                        updateCinemaHall(rowNum, seatNum);
                        break;
                    } else {
                        System.out.println("That ticket has already been purchased!");
                    }
                }
            } else if (choice == 3) {
                int totalTicketsPurchased = findTicketSale();
                double purchasedTicketsPercentage = ((double) totalTicketsPurchased / (totalRows * seatsPerRow)) * 100;
                int currentIncome = calculateCurrentIncome();
                int totalIncome = calculateTotalIncome(totalRows, seatsPerRow);

                System.out.printf("Number of purchased tickets: %d\n",totalTicketsPurchased);
                System.out.printf("Percentage: %.2f%s\n",purchasedTicketsPercentage, "%");
                System.out.printf("Current income: $%d\n", currentIncome);
                System.out.printf("Total income: $%d\n",totalIncome);

            } else {
                break;
            }
        }
    }

    static int calculateCurrentIncome() {
        int sum = 0;
        for (int i = 1; i < cinemaHall.length; i++) {
            for (int j = 1; j < cinemaHall[0].length; j++) {
                if (cinemaHall[i][j].equals("B")) {
                    sum += calculateTicketPrice(i, j);
                }
            }
        }
        return sum;
    }

    static int findTicketSale() {
        int count = 0;
        for (String[] i : cinemaHall) {
            for (String j : i) {
                if (j.equals("B")) {
                    count++;
                }
            }
        }
        return count;
    }

    private static void updateCinemaHall(int rowNum, int seatNum) {
            cinemaHall[rowNum][seatNum] = "B";
    }

    static int calculateTicketPrice(int rowNum, int seatNum) {
        int totalFrontRows = (cinemaHall.length - 1) / 2;
        System.out.println(totalFrontRows);
        if (rowNum > totalFrontRows) {
            return 8;
        } else {
            return 10;
        }
    }

    static void setCinemaHall(int totalRows, int seatsPerRow) {
        for (int i = 0; i <= totalRows; i++) {
            for (int j = 0; j <= seatsPerRow; j++) {
                if (i == 0 && j == 0) {
                    cinemaHall[i][j] = " ";
                } else if (i == 0) {
                    cinemaHall[0][j] = Integer.toString(j);
                } else if (j == 0) {
                    cinemaHall[i][0] = Integer.toString(i);
                } else {
                    cinemaHall[i][j] = "S";
                }
            }
        }
    }

    static int calculateTotalIncome(int totalRows, int seatsPerRow) {
        if (totalRows * seatsPerRow <= 60) {
            return seatsPerRow * totalRows * 10;
        } else {
            int totalFrontSeats = (totalRows / 2) * seatsPerRow;
            int totalBackSeats = (totalRows - (totalRows / 2)) * seatsPerRow;
            return (totalFrontSeats * 10) + (totalBackSeats * 8);
        }
    }

    static void printCinemaHall() {
        System.out.println("Cinema:");
        for (String[] i : cinemaHall) {
            for (String j : i) {
                if (j.equals(" ")) {
                    System.out.print("  ");
                    continue;
                }
                System.out.printf("%s ", j);
            }
            System.out.println();
        }
    }
}