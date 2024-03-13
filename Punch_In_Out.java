import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Punch_In_Out {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press Enter to clock-in.");
        scanner.nextLine();

        Instant startTime = Instant.now();
        LocalDateTime startDateTime = LocalDateTime.now();

        System.out.println("Clocked-in. Press Enter to clock-out.");
        scanner.nextLine();

        Instant stopTime = Instant.now();
        LocalDateTime stopDateTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, stopTime);
        long seconds = duration.getSeconds();
        double hours = seconds / 3600.0;

        double roundedHours = Math.round(hours * 4) / 4.0;

        System.out.println("Clocked-out.");
        System.out.println("Paid time: " + roundedHours + " hours.");

        // Write data to text file
        writeToFile(startDateTime, stopDateTime, roundedHours);

        scanner.close();
    }

    private static void writeToFile(LocalDateTime startDateTime, LocalDateTime stopDateTime, double elapsedHours) {
        try {
            FileWriter writer = new FileWriter("punch_in_out_records.txt", true);

            // Format date-time objects
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Write start and stop time along with elapsed hours to file
            writer.write("Start Time: " + startDateTime.format(formatter) + "\n");
            writer.write("Stop Time: " + stopDateTime.format(formatter) + "\n");
            writer.write("Elapsed Time: " + elapsedHours + " hours\n\n");

            writer.close();
            System.out.println("Data has been written to punch_in_out_records.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }
}


