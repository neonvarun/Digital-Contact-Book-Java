import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ContactBookApp {

    private JFrame frame;
    private JTextField nameField;
    private JTextField numberField;
    private JTextField emailField;
    private JTextField zipcodeField;
    private JTextField cityField;
    private JComboBox<String> stateComboBox;
    private JList<String> contactList;
    private DefaultListModel<String> listModel;
    private ArrayList<Contact> contacts;

    private static class Contact {
        String name, number, email, zipcode, city, state;

        Contact(String name, String number, String email, String zipcode, String city, String state) {
            this.name = name;
            this.number = number;
            this.email = email;
            this.zipcode = zipcode;
            this.city = city;
            this.state = state;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Contact contact = (Contact) obj;
            return name.equals(contact.name) && number.equals(contact.number);
        }

        @Override
        public int hashCode() {
            return name.hashCode() + number.hashCode();
        }
    }

    private static final Set<String> VALID_STATES = new HashSet<>();
    static {
        Collections.addAll(VALID_STATES, "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala",
                "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
                "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal");
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactBookApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Digital ContactBook");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        contacts = new ArrayList<>();
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(contactList);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("NAME:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("PHONE NO:"));
        numberField = new JTextField();
        panel.add(numberField);

        panel.add(new JLabel("EMAIL:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Zipcode:"));
        zipcodeField = new JTextField();
        panel.add(zipcodeField);

        panel.add(new JLabel("City:"));
        cityField = new JTextField();
        panel.add(cityField);

        panel.add(new JLabel("State:"));
        stateComboBox = new JComboBox<>(new String[] {
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala",
                "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
                "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal"
        });
        panel.add(stateComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 6));

        JButton addButton = new JButton("ADD");
        JButton viewButton = new JButton("VIEW");
        JButton editButton = new JButton("EDIT");
        JButton deleteButton = new JButton("DELETE");
        JButton resetButton = new JButton("RESET");
        JButton exitButton = new JButton("EXIT");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewContact();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText().trim();
        String number = numberField.getText().trim();
        String email = emailField.getText().trim();
        String zipcode = zipcodeField.getText().trim();
        String city = cityField.getText().trim();
        String state = (String) stateComboBox.getSelectedItem();

        if (name.isEmpty() || number.isEmpty() || email.isEmpty() || zipcode.isEmpty() || city.isEmpty()
                || state == null) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(frame, "Name can only contain letters and spaces.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!city.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(frame, "City can only contain letters and spaces.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!VALID_STATES.contains(state)) {
            JOptionPane.showMessageDialog(frame, "Invalid state selected.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches(EMAIL_PATTERN.pattern())) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!number.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Phone number must contain only digits.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!zipcode.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Zipcode must contain only digits.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contact newContact = new Contact(name, number, email, zipcode, city, state);
        if (contacts.contains(newContact)) {
            JOptionPane.showMessageDialog(frame, "Contact already exists.", "Duplicate Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        contacts.add(newContact);
        updateContactList();
        resetFields();
    }

    private void viewContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Contact contact = contacts.get(selectedIndex);
            nameField.setText(contact.name);
            numberField.setText(contact.number);
            emailField.setText(contact.email);
            zipcodeField.setText(contact.zipcode);
            cityField.setText(contact.city);
            stateComboBox.setSelectedItem(contact.state);
        }
    }

    private void editContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            String name = nameField.getText().trim();
            String number = numberField.getText().trim();
            String email = emailField.getText().trim();
            String zipcode = zipcodeField.getText().trim();
            String city = cityField.getText().trim();
            String state = (String) stateComboBox.getSelectedItem();

            if (name.isEmpty() || number.isEmpty() || email.isEmpty() || zipcode.isEmpty() || city.isEmpty()
                    || state == null) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(frame, "Name can only contain letters and spaces.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!city.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(frame, "City can only contain letters and spaces.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!VALID_STATES.contains(state)) {
                JOptionPane.showMessageDialog(frame, "Invalid state selected.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches(EMAIL_PATTERN.pattern())) {
                JOptionPane.showMessageDialog(frame, "Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!number.matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "Phone number must contain only digits.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!zipcode.matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "Zipcode must contain only digits.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Contact updatedContact = new Contact(name, number, email, zipcode, city, state);
            contacts.set(selectedIndex, updatedContact);
            updateContactList();
        }
    }

    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex >= 0) {
            contacts.remove(selectedIndex);
            updateContactList();
            resetFields();
        }
    }

    private void resetFields() {
        nameField.setText("");
        numberField.setText("");
        emailField.setText("");
        zipcodeField.setText("");
        cityField.setText("");
        stateComboBox.setSelectedIndex(-1);
    }

    private void updateContactList() {
        listModel.clear();
        Collections.sort(contacts, (c1, c2) -> c1.name.compareTo(c2.name));
        for (Contact contact : contacts) {
            listModel.addElement(contact.toString());
        }
    }
}
