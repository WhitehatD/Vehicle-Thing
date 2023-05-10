
import javax.swing.*;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Main {

    private static JFrame frame;
    private static JPanel mainPanel, carPanel, parkingPanel, simulationPanel;

    private static List<ParkingSpot> spots;
    private static List<Vehicle> vehicles;

    public static void main(String[] args){
        ParkingGarage garage = new ParkingGarage();

        garage.addSpots(
                new ParkingSpot(1, 10, ParkingSpotType.LARGE),
                new ParkingSpot(2, 15, ParkingSpotType.LARGE),
                new ParkingSpot(3, 9, ParkingSpotType.SMALL),
                new ParkingSpot(4, 8, ParkingSpotType.SMALL),
                new ParkingSpot(5, 7, ParkingSpotType.LARGE));

        garage.reserveParkingSpot(new Vehicle(1, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(2, VehicleTypes.CAR));
        garage.reserveParkingSpot(new Vehicle(3, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(4, VehicleTypes.TRUCK));
        garage.reserveParkingSpot(new Vehicle(5, VehicleTypes.TRUCK));

        vehicles = new ArrayList<>();
        spots = new ArrayList<>();

        garage.output();

        initMainPanel();
        initCarPanel();
        initParkingPanel();

        initMainFrame();

    }

    private static void initMainPanel(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);

        JButton parkingSpotsButton = new JButton(
                "<html><center>Configureaza locurile de parcare disponibile</center></html>");
        parkingSpotsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        parkingSpotsButton.setBackground(Color.ORANGE);
        parkingSpotsButton.setPreferredSize(new Dimension(200, 100));
        parkingSpotsButton.setFocusPainted(false);

        parkingSpotsButton.addActionListener(e -> {
            switchFrame(mainPanel, parkingPanel);
        });



        JButton carsButton = new JButton(
                "<html><center>Configureaza autovehiculele</center></html>");
        carsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        carsButton.setBackground(Color.ORANGE);
        carsButton.setPreferredSize(new Dimension(200, 100));
        carsButton.setFocusPainted(false);

        carsButton.addActionListener(e -> {
            switchFrame(mainPanel, carPanel);
        });


        topPanel.add(parkingSpotsButton);
        topPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        topPanel.add(carsButton);



        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton simulationButton = new JButton(
                "<html><center>Porneste simularea</center></html>"
        );
        simulationButton.setFont(new Font("Arial", Font.PLAIN, 20));
        simulationButton.setBackground(Color.GREEN);
        simulationButton.setPreferredSize(new Dimension(200,100));
        simulationButton.setFocusPainted(false);

        simulationButton.addActionListener(e -> {
            initSimulationPanel();
            switchFrame(mainPanel, simulationPanel);
        });

        bottomPanel.add(simulationButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private static void initMainFrame(){
        frame = new JFrame("Configurator parcari");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private static void initCarPanel(){
        carPanel = new JPanel(new BorderLayout());
        carPanel.setBackground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);

        JLabel choose = new JLabel("Alege o optiune:");
        choose.setFont(new Font("Arial", Font.PLAIN, 20));

        JComboBox<VehicleTypes> box = new JComboBox<>(VehicleTypes.values());
        box.setFont(new Font("Arial", Font.PLAIN, 20));


        JButton backButton = new JButton(
                "<html><center>Inapoi</center></html>");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setBackground(Color.PINK);
        backButton.setPreferredSize(new Dimension(150, 70));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            switchFrame(carPanel, mainPanel);
        });

        topPanel.add(choose);
        topPanel.add(box);
        topPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        topPanel.add(backButton);


        JPanel midLeftPanel = new JPanel();
        midLeftPanel.setBackground(Color.LIGHT_GRAY);

        JLabel list = new JLabel("Lista autovehicule (maxim 10):");
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        midLeftPanel.add(list);

        JPanel midRightPanel = new JPanel();
        midRightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel display = new JLabel("");
        display.setFont(new Font("Arial", Font.PLAIN, 15));
        midRightPanel.add(display);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton addButton = new JButton(
                "<html><center>Adauga</center></html>");
        addButton.setFont(new Font("Arial", Font.PLAIN, 20));
        addButton.setBackground(Color.GREEN);
        addButton.setPreferredSize(new Dimension(150, 70));
        addButton.setFocusPainted(false);

        addButton.addActionListener(e -> {
            if(vehicles.size() == 10) return;

            Vehicle vehicleToAdd = new Vehicle(vehicles.size() + 1, (VehicleTypes) box.getSelectedItem());
            vehicles.add(vehicleToAdd);

            String result = "";

            for(Vehicle vehicle : vehicles){
                result = result + "ID: " + vehicle.getId() + ", Type: " + vehicle.getType().toString() + "<br>";
            }

            display.setText("<html>" + result + "</html>");
        });

        bottomPanel.add(addButton);

        JButton removeButton = new JButton(
                "<html><center>Sterge lista</center></html>");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        removeButton.setBackground(Color.RED);
        removeButton.setPreferredSize(new Dimension(150, 70));
        removeButton.setFocusPainted(false);

        removeButton.addActionListener(e -> {
            vehicles.clear();

            display.setText("");
        });

        bottomPanel.add(removeButton);

        carPanel.add(topPanel, BorderLayout.PAGE_START);
        carPanel.add(midLeftPanel, BorderLayout.LINE_START);
        carPanel.add(midRightPanel, BorderLayout.CENTER);
        carPanel.add(bottomPanel, BorderLayout.PAGE_END);

    }

    private static void initParkingPanel(){
        parkingPanel = new JPanel(new BorderLayout());
        parkingPanel.setBackground(Color.LIGHT_GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);

        JLabel choose = new JLabel("Alege o optiune:");
        choose.setFont(new Font("Arial", Font.PLAIN, 20));

        JComboBox<ParkingSpotType> box = new JComboBox<>(ParkingSpotType.values());
        box.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel price = new JLabel("Pret:");
        price.setFont(new Font("Arial", Font.PLAIN, 20));

        SpinnerModel model = new SpinnerNumberModel(9.9, 0.1, 10000, 0.1);
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(120, 32));
        spinner.setUI(new CustomSpinnerUI());

        JFormattedTextField formattedTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        formattedTextField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        formattedTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        formattedTextField.setBackground(Color.WHITE);

        // Doar numere vor putea fi introduse in Spinner
        DefaultFormatter formatter = (DefaultFormatter) formattedTextField.getFormatter();
        formatter.setAllowsInvalid(false);


        JButton backButton = new JButton(
                "<html><center>Inapoi</center></html>");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.setBackground(Color.PINK);
        backButton.setPreferredSize(new Dimension(150, 70));
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            switchFrame(parkingPanel, mainPanel);
        });

        topPanel.add(choose);
        topPanel.add(box);
        topPanel.add(price);
        topPanel.add(spinner);
        topPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        topPanel.add(backButton);


        JPanel midLeftPanel = new JPanel();
        midLeftPanel.setBackground(Color.LIGHT_GRAY);

        JLabel list = new JLabel("Lista parcari (maxim 10):");
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        midLeftPanel.add(list);

        JPanel midRightPanel = new JPanel();
        midRightPanel.setBackground(Color.LIGHT_GRAY);

        JLabel display = new JLabel("");
        display.setFont(new Font("Arial", Font.PLAIN, 15));
        midRightPanel.add(display);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton addButton = new JButton(
                "<html><center>Adauga</center></html>");
        addButton.setFont(new Font("Arial", Font.PLAIN, 20));
        addButton.setBackground(Color.GREEN);
        addButton.setPreferredSize(new Dimension(150, 70));
        addButton.setFocusPainted(false);

        addButton.addActionListener(e -> {
            if(spots.size() == 10) return;

            ParkingSpot spot = new ParkingSpot(
                    spots.size() + 1,
                    (Double) spinner.getValue(),
                    (ParkingSpotType) box.getSelectedItem());
            spots.add(spot);

            String result = "";

            for(ParkingSpot parkingSpot : spots){
                NumberFormat format = new DecimalFormat("#0.00");


                result = result + "ID: " + parkingSpot.getId() + ", Price: " + format.format(parkingSpot.getPrice()) +
                        ", Type: " + parkingSpot.getType().toString() + "<br>";
            }

            display.setText("<html>" + result + "</html>");
        });

        bottomPanel.add(addButton);

        JButton removeButton = new JButton(
                "<html><center>Sterge lista</center></html>");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        removeButton.setBackground(Color.RED);
        removeButton.setPreferredSize(new Dimension(150, 70));
        removeButton.setFocusPainted(false);

        removeButton.addActionListener(e -> {
            spots.clear();

            display.setText("");
        });

        bottomPanel.add(removeButton);

        parkingPanel.add(topPanel, BorderLayout.PAGE_START);
        parkingPanel.add(midLeftPanel, BorderLayout.LINE_START);
        parkingPanel.add(midRightPanel, BorderLayout.CENTER);
        parkingPanel.add(bottomPanel, BorderLayout.PAGE_END);

    }

    private static void initSimulationPanel(){
        simulationPanel = new JPanel(new BorderLayout());
        simulationPanel.setBackground(Color.LIGHT_GRAY);

        JPanel parkings = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        parkings.setBackground(Color.LIGHT_GRAY);

        ParkingGarage garage = new ParkingGarage();
        garage.addSpots(spots.toArray(new ParkingSpot[0]));

        for(Vehicle vehicle : vehicles){
            garage.reserveParkingSpot(vehicle);
        }

        for(Map.Entry<ParkingSpot, Vehicle> entry : garage.getParkingSpots().entrySet()){
            Vehicle vehicle = entry.getValue();
            ParkingSpot spot = entry.getKey();

            Color color;
            String text;

            //inseamna ca locul a fost ocupat
            if(vehicle == null){
                color = Color.GREEN;
                text = "<html><center>" + spot.getId() + "<br><br>Neocupat<br><br> " + spot.getType() + "</center></html>";
            } else {
                color = Color.RED;
                text = "<html><center>" + spot.getId() + "<br><br>Ocupat<br>ID: "
                        + vehicle.getId() + "<br> " + vehicle.getType() + "<br><br> " + spot.getType() + " </center></html>";
            }

            Dimension size = new Dimension(60, 150);
            JPanel parkingSpot = new JPanel();

            JLabel label = new JLabel(text);
            parkingSpot.add(label);

            parkingSpot.setPreferredSize(size);
            parkingSpot.setBackground(color);

            parkings.add(parkingSpot);
        }

        simulationPanel.add(parkings, BorderLayout.PAGE_START);

        JPanel notOccupied = new JPanel();
        notOccupied.setBackground(Color.LIGHT_GRAY);
        JLabel notOccupiedLabel = new JLabel();
        notOccupiedLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        notOccupied.add(notOccupiedLabel);

        String result = "";

        for(Vehicle vehicle : garage.getLeftOutVehicles()){
            result = result + "ID: " + vehicle.getId() + ", Type: " + vehicle.getType().toString() + "<br>";
        }

        if(result.equals(""))
            notOccupiedLabel.setText("Toate autovehiculele au loc de parcare!");
        else
        notOccupiedLabel.setText("<html> Autovehicule fara loc: <br>" + result + "</html>");

        simulationPanel.add(notOccupied, BorderLayout.CENTER);

    }

    private static void switchFrame(JPanel from, JPanel to){
        from.setVisible(false);
        to.setVisible(true);
        frame.setContentPane(to);
    }


    // Custom spinner UI
    static class CustomSpinnerUI extends BasicSpinnerUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            spinner.setBorder(null);
        }
    }

}
