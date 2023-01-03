package ProjektInz.RESTAPI.restApi.Allegro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllegroAuthorizationCodeToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private boolean allegro_api;
    private String jti;

    public static String accessToken = "";
    public static String refreshToken = "";
}
