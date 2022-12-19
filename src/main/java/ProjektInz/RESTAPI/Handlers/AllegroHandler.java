package ProjektInz.RESTAPI.Handlers;

import ProjektInz.RESTAPI.repository.CodeEntityRepository;
import ProjektInz.RESTAPI.restApi.CodeEntity;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class AllegroHandler implements HttpHandler  {

    private CodeEntityRepository codeEntityRepository;

    public AllegroHandler(CodeEntityRepository codeEntityRepository){
        this.codeEntityRepository = codeEntityRepository;
    }

    @Override
    public void handle(HttpExchange t) {
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(String.valueOf(t.getRequestURI())).build().getQueryParams();
        String param1 = parameters.get("code").toString();
        param1 = param1.replace("[", "").replace("]", "").replace(" ", "");
        CodeEntity codeEntity = new CodeEntity(param1);
        codeEntityRepository.saveAndFlush(codeEntity);
        t.close();
    }
}
