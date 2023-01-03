package ProjektInz.RESTAPI.restApi.Olx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OlxToken {
    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;

    public static String accessToken = "";
}
