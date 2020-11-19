package hmim.eteam.rest.backend.representation.output;

public class ResultCode extends TypedResponse {
    public final int code;

    public ResultCode(int code) {
        this.code = code;
    }

    public enum Codes {
        OK,
        INTERNAL_ERROR,
        INCORRECT_INPUT_FORMAT,

        LOGIN_TAKEN,
        LOGIN_FAILED,

        NOT_EXISTING_FORUM_THEME,
        NOT_EXISTING_COURSE,
    }
}
