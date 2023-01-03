package ProjektInz.RESTAPI.restApi.Allegro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AllegroToken {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private boolean allegro_api;
    private String jti;
}
