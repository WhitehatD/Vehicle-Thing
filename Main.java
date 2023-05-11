
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Main {

    public static JFrame frame;
    private static JPanel mainPanel, carPanel, parkingPanel, simulationPanel, loginPanel;

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

        initLoginPanel();
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

        frame.setContentPane(loginPanel);
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

        garage.output();

    }

    private static void initLoginPanel() {
        loginPanel = new JPanel();
        GroupLayout layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(layout);

        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginPanel.setBackground(Color.LIGHT_GRAY);

        // Username Label
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.LIGHT_GRAY);

        JLabel usernameLabel = new JLabel("Utilizator:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(usernameLabel, BorderLayout.NORTH);

        // Username Text Field
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
        topPanel.add(usernameField, BorderLayout.CENTER);

        JPanel midPanel = new JPanel(new BorderLayout());
        midPanel.setBackground(Color.LIGHT_GRAY);

        // Password Label
        JLabel passwordLabel = new JLabel("Parola:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        midPanel.add(passwordLabel, BorderLayout.NORTH);

        // Password Field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        midPanel.add(passwordField, BorderLayout.PAGE_END);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.setBackground(Color.GREEN);
        loginButton.setPreferredSize(new Dimension(150, 70));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                switchFrame(loginPanel, mainPanel);
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Username sau parola incorecte.");
            }
        });

        bottomPanel.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        registerButton.setBackground(Color.CYAN);
        registerButton.setPreferredSize(new Dimension(150, 70));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(mainPanel, "Te-ai inregistrat cu succes!");
                }
            }
        });

        bottomPanel.add(registerButton);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addGap(10)
                        .addComponent(usernameField))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(passwordLabel)
                        .addGap(10)
                        .addComponent(passwordField))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(loginButton)
                        .addGap(10)
                        .addComponent(registerButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField))
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(registerButton))
        );

    }

    private static boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                String storedUsername = user[0];
                String storedPassword = user[1];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean registerUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginPanel, "Userul sau parola nu sunt completate.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                String storedUsername = user[0];
                if (username.equals(storedUsername)) {
                    JOptionPane.showMessageDialog(loginPanel, "Deja exista un utilizator cu acest nume.");
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
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
