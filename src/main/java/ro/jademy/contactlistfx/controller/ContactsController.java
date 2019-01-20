package ro.jademy.contactlistfx.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ro.jademy.contactlistfx.io.ContactReader;
import ro.jademy.contactlistfx.model.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContactsController {

    private ContactReader contactReader;

    private Map<String, Contact> contacts = new HashMap<>();

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
    private Label phoneNumber;

    public ContactsController() {
        this(new ContactReader());
    }

    private ContactsController(ContactReader contactReader) {
        this.contactReader = contactReader;

        List<Contact> contactList = contactReader.getContacts();
        contactList.forEach(c -> contacts.put(c.getFullName(), c));
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
                    phoneNumber.setText(selectedContact.getPhoneNumber());
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

        String selectedName = contactsListView.getSelectionModel().getSelectedItem();
        Contact selectedContact = contacts.get(selectedName);

        TextInputDialog dialog = new TextInputDialog(selectedContact.getPhoneNumber());
        dialog.setTitle("Edit Phone Number");
        dialog.setHeaderText(null);
        dialog.setContentText("Phone Number:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            selectedContact.setPhoneNumber(result.get());
            phoneNumber.setText(result.get());
        }
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

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Phone Number:"), 0, 2);
        grid.add(phoneNumber, 1, 2);

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
                return new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText());
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
