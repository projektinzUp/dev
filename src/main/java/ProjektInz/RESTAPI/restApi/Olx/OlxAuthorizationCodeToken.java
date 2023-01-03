package ProjektInz.RESTAPI.restApi.Olx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OlxAuthorizationCodeToken {

    private String access_token;
    private String expires_in;
    private String token_type;
    private String scope;
    private String refresh_token;

    public static String accessToken = "";
    public static String refreshToken = "";

}
