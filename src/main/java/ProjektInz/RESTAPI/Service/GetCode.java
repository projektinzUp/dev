package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.repository.CodeEntityRepository;
import ProjektInz.RESTAPI.restApi.CodeEntity;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;


public class GetCode {
    private UriComponentsBuilder builder;
    private int port;
    private String fileName;

    @Autowired
    private CodeEntityRepository codeEntityRepository;
    public GetCode(UriComponentsBuilder builder, int port, String file){
        this.builder = builder;
        this.port = port;
        this.fileName = file;
    }

    public void getCode() throws Exception {
        URI requestUri = builder.build(true).toUri();
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(requestUri);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        CodeEntity codeEntity = new CodeEntity("test");
        codeEntityRepository.save(codeEntity);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null);
        server.start();
    }

    public static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            System.out.println("WEszlem kurwa maxcUFUSD");
            MultiValueMap<String, String> parameters =
                    UriComponentsBuilder.fromUriString(String.valueOf(t.getRequestURI())).build().getQueryParams();
            String param1 = parameters.get("code").toString();
            param1 = param1.replace("[", "").replace("]", "").replace(" ", "");

        }

    }


}
