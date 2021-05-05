package pl.lodz.p.it.tks.rent.soap;

import lombok.SneakyThrows;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    private static final String LOGINAPI_URI = "http://localhost:8080/UserSoap/LoginAPI?wsdl";

    public static void authenticateUser(BindingProvider bindingProvider, String user, String password) {
        Map<String, List<String>> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", Collections.singletonList("Bearer " + authenticate(user, password)));
        bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
    }


    @SneakyThrows
    private static String authenticate(String user, String password) {
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:a=\"http://auth.soap.user.tks.it.p.lodz.pl/\" " +
                "xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <SOAP-ENV:Header/>\n" +
                "    <S:Body>\n" +
                "        <a:authenticate>\n" +
                "            <login>" + user + "</login>\n" +
                "            <password>" + password + "</password>\n" +
                "        </a:authenticate>\n" +
                "    </S:Body>\n" +
                "</S:Envelope>";

        URL url = new URL(LOGINAPI_URI);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set timeout as per needs
        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);

        // Set DoOutput to true if you want to use URLConnection for output.
        // Default is false
        connection.setDoOutput(true);

        connection.setUseCaches(true);
        connection.setRequestMethod("POST");

        // Set Headers
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Content-Type", "text/xml");

        // Write XML
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = request.getBytes(StandardCharsets.UTF_8);
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();

        // Read XML
        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(
                new MimeHeaders(),
                new ByteArrayInputStream(response.toString().getBytes(StandardCharsets.UTF_8)));

        SOAPBody body = message.getSOAPBody();

        return body.getElementsByTagName("ns2:authenticateResponse").item(0).getFirstChild().getTextContent();
    }
}
