package ro.jademy.contactlistfx.controller;

import ro.jademy.contactlistfx.ApplicationContext;
import ro.jademy.contactlistfx.Main;

public abstract class BaseController {

    public ApplicationContext getContext() {
        return Main.getContext();
    }
}
