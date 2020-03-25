package ro.jademy.contactlistfx.controller;

import com.github.javafaker.Faker;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ro.jademy.contactlistfx.model.Address;
import ro.jademy.contactlistfx.model.Contact;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContactsController extends BaseController {

    private Map<String, Contact> contacts;

    @FXML
    private ListView<String> contactsListView;

    @FXML
    private VBox messageVBox;
    @FXML
    private VBox detailsVBox;

    @FXML
    private Button addContactButton;

    @FXML
    private Button backButton;
    @FXML
    private Button editButton;

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phoneNumbers;
    @FXML
    private Label address;

    public ContactsController() {
        this.contacts = initContacts();
    }

    private Map<String, Contact> initContacts() {
        Faker faker = new Faker();
        Map<String, Contact> contacts = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            Contact contact = new Contact(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber());

            Address address = new Address();
            address.setCity(faker.address().cityName());
            address.setStreetName(faker.address().streetName());
            address.setNumber(faker.address().streetAddressNumber());
            contact.setAddress(address);

            contacts.put(contact.getFullName(), contact);
        }

        return contacts;
    }

    @FXML
    public void initialize() {
        // Populate the contact list view
        contacts.keySet().forEach(s -> contactsListView.getItems().add(s));

        contactsListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
            if (c.next()) {
                if (c.wasAdded()) {
                    // Hide the message and show the details pane
                    messageVBox.setVisible(false);
                    detailsVBox.setVisible(true);

                    String selectedName = c.getAddedSubList().get(0);
                    Contact selectedContact = contacts.get(selectedName);

                    firstName.setText(selectedContact.getFirstName());
                    lastName.setText(selectedContact.getLastName());
                    phoneNumbers.setText(String.join(", ", selectedContact.getPhoneNumbers().values()));
                    address.setText(selectedContact.getAddress().toString());
                }
            }
        });

        addContactButton.setOnAction(event -> handleAdd());

        backButton.setOnAction(event -> {
            messageVBox.setVisible(true);
            detailsVBox.setVisible(false);
            contactsListView.getSelectionModel().clearSelection();
        });

        editButton.setOnAction(event -> handleEdit());
    }

    private void handleEdit() {
        // TODO: implement edit contact
        System.out.println("Not yet implemented!");
    }

    private void handleAdd() {
        // Create the custom dialog
        Dialog<Contact> dialog = new Dialog<>();
        dialog.setTitle("Add Contact");
        dialog.setHeaderText(null);

        // Set the button types
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create the labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone Number");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField street = new TextField();
        street.setPromptText("Street");
        TextField streetNumber = new TextField();
        streetNumber.setPromptText("Street Number");

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Phone Number:"), 0, 2);
        grid.add(phoneNumber, 1, 2);
        grid.add(new Label("City:"), 0, 3);
        grid.add(city, 1, 3);
        grid.add(new Label("Street:"), 0, 4);
        grid.add(street, 1, 4);
        grid.add(new Label("Street Number:"), 0, 5);
        grid.add(streetNumber, 1, 5);

        // Disable the OK button by default
        Node okButtonNode = dialog.getDialogPane().lookupButton(okButton);
        okButtonNode.setDisable(true);

        // Create a binding between the OK button and the fields using the JavaFX Bindings API
        // The OK button won't be enabled unless all fields are filled
        BooleanBinding fieldBinding = Bindings.isEmpty(firstName.textProperty())
                .or(Bindings.isEmpty(lastName.textProperty()))
                .or(Bindings.isEmpty(phoneNumber.textProperty()));
        okButtonNode.disableProperty().bind(fieldBinding);

        // Set the dialog content
        dialog.getDialogPane().setContent(grid);

        // Convert the result to a Contact object when the OK button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), new Address(city.getText(), street.getText(), streetNumber.getText()));
            }
            return null;
        });

        Optional<Contact> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            Contact c = result.get();
            String cName = c.getFirstName() + " " + c.getLastName();
            contactsListView.getItems().add(cName);
            contacts.put(cName, c);
        });
    }

}
