import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch_In_Out_GUI {
    private JFrame frame;
    private JButton startButton;
    private JButton stopButton;
    private JLabel statusLabel;

    private Instant startTime;
    private LocalDateTime startDateTime;
    private Instant stopTime;
    private LocalDateTime stopDateTime;

    public Punch_In_Out_GUI() {
        frame = new JFrame("Punch In/Out");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        startButton = new JButton("PUNCH IN");
        stopButton = new JButton("PUNCH OUT");
        statusLabel = new JLabel("Press 'PUNCH IN' to begin recording.");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        frame.add(startButton);
        frame.add(stopButton);
        frame.add(statusLabel);

        frame.pack();
        frame.setVisible(true);
    }

    private void start() {
        startTime = Instant.now();
        startDateTime = LocalDateTime.now();
        statusLabel.setText("Clocked in. Press 'PUNCH OUT' to end recording.");
    }

    private void stop() {
        stopTime = Instant.now();
        stopDateTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, stopTime);
        long seconds = duration.getSeconds();
        double hours = seconds / 3600.0;
        double roundedHours = Math.round(hours * 4) / 4.0;

        // Write data to text file
        writeToFile(startDateTime, stopDateTime, roundedHours);

        statusLabel.setText("Clocked out. Paid time: " + roundedHours + " hours.");
    }

    private void writeToFile(LocalDateTime startDateTime, LocalDateTime stopDateTime, double elapsedHours) {
        try {
            FileWriter writer = new FileWriter("punch_in_out_records.txt", true);

            // Format date-time objects
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Write start and stop time along with elapsed hours to file
            writer.write("Clock In Time: " + startDateTime.format(formatter) + "\n");
            writer.write("Clock Out Time: " + stopDateTime.format(formatter) + "\n");
            writer.write("Paid Time: " + elapsedHours + " hours\n\n");

            writer.close();
            System.out.println("Data has been written to punch_in_out_records.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Punch_In_Out_GUI();
            }
        });
    }
}
