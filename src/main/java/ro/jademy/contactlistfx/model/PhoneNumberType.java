package ro.jademy.contactlistfx.model;

public enum PhoneNumberType {

    HOME("home"),
    WORK("work");

    private String displayName;

    PhoneNumberType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
