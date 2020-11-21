package hmim.eteam.rest.backend.entity.user;

import hmim.eteam.rest.backend.model.CourseRole;

import java.util.logging.Logger;

public enum UserRole {
    Guest,
    Student,
    Admin;

    public CourseRole.NameEnum toApiRepresentation() {
        switch (this) {
            case Guest:
                return CourseRole.NameEnum.GUEST;
            case Student:
                return CourseRole.NameEnum.STUDENT;
            case Admin:
                return CourseRole.NameEnum.ADMIN;
            default:
                Logger.getLogger(getClass().getSimpleName(),
                        "Unable to convert enum value " + this + " to api representation!");
                return CourseRole.NameEnum.GUEST;
        }
    }
}
