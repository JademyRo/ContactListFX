package ro.jademy.contactlistfx.io;

import ro.jademy.contactlistfx.model.Contact;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactReader {

    private static final String DEFAULT_CONTACTS_FILE_NAME = "/files/contacts.csv";


    public List<Contact> getContacts() {
        return getContacts(DEFAULT_CONTACTS_FILE_NAME);
    }

    public List<Contact> getContacts(String fileName) {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(getClass().getResource(fileName).getFile()))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] contactParts = line.split(",");
                contacts.add(new Contact(contactParts[0], contactParts[1], contactParts[2]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to open file " + fileName + "\n" + ex);
        } catch (IOException ex) {
            System.out.println("Failed to read content from file " + fileName + "\n" + ex);
        }

        return contacts;
    }
}
