package hmim.eteam.rest.backend.representation.output;

public class ResultCode extends TypedResponse {
    public final int code;

    public ResultCode(int code) {
        this.code = code;
    }

    public enum Codes
    {
        OK,
        INCORRECT_INPUT_FORMAT,

    }
}
