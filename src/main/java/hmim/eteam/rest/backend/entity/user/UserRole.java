package hmim.eteam.rest.backend.entity.user;

import hmim.eteam.rest.backend.model.Role;

import java.util.logging.Logger;

public enum UserRole {
    Guest,
    Student,
    Admin;

    public Role toApiRepresentation() {
        switch (this) {
            case Guest:
                return Role.GUEST;
            case Student:
                return Role.STUDENT;
            case Admin:
                return Role.ADMIN;
            default:
                Logger.getLogger(getClass().getSimpleName(),
                        "Unable to convert enum value " + this + " to api representation!");
                return Role.GUEST;
        }
    }
}
