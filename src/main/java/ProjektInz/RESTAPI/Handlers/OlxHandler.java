package ProjektInz.RESTAPI.Handlers;


import ProjektInz.RESTAPI.repository.OlxCodeEntityRepository;
import ProjektInz.RESTAPI.restApi.CodeEntity;
import ProjektInz.RESTAPI.restApi.OlxCodeEntity;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


public class OlxHandler implements HttpHandler {

    private OlxCodeEntityRepository olxCodeEntityRepository;

    public OlxHandler(OlxCodeEntityRepository olxCodeEntityRepository) {
        this.olxCodeEntityRepository = olxCodeEntityRepository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        MultiValueMap<String, String> parameters =
                UriComponentsBuilder.fromUriString(String.valueOf(exchange.getRequestURI())).build().getQueryParams();
        String param1 = parameters.get("code").toString();
        param1 = param1.replace("[", "").replace("]", "").replace(" ", "");
        OlxCodeEntity codeEntity = new OlxCodeEntity(param1);
        olxCodeEntityRepository.saveAndFlush(codeEntity);
        exchange.close();
    }
}

