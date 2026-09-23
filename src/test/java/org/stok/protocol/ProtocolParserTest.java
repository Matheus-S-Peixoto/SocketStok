package org.stok.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.stok.exceptions.ProtocolException;
import org.stok.exceptions.ServiceException;
import org.stok.protocol.pojo.CompleteResponseExemple;
import org.stok.protocol.pojo.RequestExemple;
import org.stok.protocol.pojo.ResDataExample;
import org.stok.protocol.pojo.ResponseExemple;
import org.stok.protocol.request.Request;
import org.stok.protocol.response.Response;
import org.stok.repository.ProductRepository;
import org.stok.service.StokService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolParserTest {
    private final ProtocolParser parser = new ProtocolParser();
    private final String requestExemple1 = "" +
            "{\n" +
            "  \"action\": \"P_CREATE\"\n" +
            "}";
    private final String requestExemple2 = "{\n" +
            "  \"action\": \"P_CREATE\",\n" +
            "  \"id\": null,\n" +
            "  \"body\": {\n" +
            "    \"name\": \"Amortecedor Bosche Porche Cayenne\",\n" +
            "    \"description\": \"Amortecedor dianteiro esquerdo da porche Cayenne 2013 importada\",\n" +
            "    \"amount\": 3050.43,\n" +
            "    \"code\": \"PCAUT0000000051\"\n" +
            "  }\n" +
            "}";

    @Test
    void simpleJSONscenarioParsingJsonToNode() throws JsonProcessingException {
        JsonNode node = parser.jsonToNode(requestExemple1);

        System.out.println(node);

        assertEquals("P_CREATE", node.get("action").asText());
    }

    @Test
    void simpleJSONscenarioParsingNodeToClass() throws JsonProcessingException {
        JsonNode node = parser.jsonToNode(requestExemple1);
        RequestExemple req = parser.nodeToClass(node, RequestExemple.class);

        System.out.println("Action: " + req.getAction());

        assertEquals(Actions.P_CREATE, req.getAction());
    }

    @Test
    void simpleJSONscenarioParsingClassToNode() {
        ResponseExemple res = new ResponseExemple();
        res.setStatusCode(200);
        res.setMessage("OK");
        JsonNode node = parser.classToNode(res);

        System.out.println(node);

        assertEquals(res.getStatusCode(), node.get("statusCode").asInt());
        assertEquals(res.getMessage(), node.get("message").asText());
    }

    @Test
    void simpleJSONscenarioParsingNodeToJson() throws JsonProcessingException {
        ResponseExemple res = new ResponseExemple();
        res.setStatusCode(200);
        res.setMessage("OK");
        JsonNode node = parser.classToNode(res);

        String json = parser.nodeToJson(node);

        System.out.println(json);

        assertEquals("{\"statusCode\":200,\"message\":\"OK\"}", json);
    }

    @Test
    void ComplexJsonScenarioParsingJsonToNode() throws JsonProcessingException {
        JsonNode node = parser.jsonToNode(requestExemple2);

        System.out.println(node);

        assertEquals("P_CREATE", node.get("action").asText());
        assertTrue(node.get("id").isNull());
        assertEquals("Amortecedor Bosche Porche Cayenne", node.get("body").get("name").asText());
        assertEquals("Amortecedor dianteiro esquerdo da porche Cayenne 2013 importada", node.get("body").get("description").asText());
        assertEquals(new BigDecimal("3050.43"), node.get("body").get("amount").decimalValue());
        assertEquals("PCAUT0000000051", node.get("body").get("code").asText());
    }

    @Test
    void ComlpexJSONscenarioParsingNodeToClass() throws JsonProcessingException {
        JsonNode node = parser.jsonToNode(requestExemple2);
        RequestExemple req = parser.nodeToClass(node, RequestExemple.class);

        System.out.println("Action: " + req.getAction());
        System.out.println("ID: " + req.getId());
        System.out.println("Body-name: " + req.getBody().getName());
        System.out.println("Body-desc: " + req.getBody().getDescription());
        System.out.println("Body-amount: " + req.getBody().getAmount());
        System.out.println("Body-code: " + req.getBody().getCode());

        assertEquals(Actions.P_CREATE, req.getAction());
        assertNull(req.getId());
        assertEquals("Amortecedor Bosche Porche Cayenne", req.getBody().getName());
        assertEquals("Amortecedor dianteiro esquerdo da porche Cayenne 2013 importada", req.getBody().getDescription());
        assertEquals(new BigDecimal("3050.43"), req.getBody().getAmount());
        assertEquals("PCAUT0000000051", req.getBody().getCode());
    }

    @Test
    void parseRequestScenarioEdit() throws JsonProcessingException {
        String completeRequestExemple1 = "" +
                "{\n" +
                "  \"action\": \"P_EDIT\",\n" +
                "  \"id\": 15,\n" +
                "  \"body\": {\n" +
                "    \"description\": \"Amortecedor dianteiro esquerdo da porche Cayenne 2012\",\n" +
                "    \"amount\": 2445.99\n" +
                "  }\n" +
                "}";

        RequestExemple req = parser.parseRequest(completeRequestExemple1, RequestExemple.class);

        System.out.println("Action: " + req.getAction());
        System.out.println("ID: " + req.getId());
        System.out.println("Body-name: " + req.getBody().getName());
        System.out.println("Body-desc: " + req.getBody().getDescription());
        System.out.println("Body-amount: " + req.getBody().getAmount());
        System.out.println("Body-code: " + req.getBody().getCode());

        assertEquals(Actions.P_EDIT, req.getAction());
        assertEquals(15, req.getId());
        assertEquals("Amortecedor dianteiro esquerdo da porche Cayenne 2012", req.getBody().getDescription());
        assertEquals(new BigDecimal("2445.99"), req.getBody().getAmount());
    }

    @Test
    void parseResponseScenarioEdit() throws JsonProcessingException {
        ResDataExample data = new ResDataExample();
        data.setDescription("Amortecedor dianteiro esquerdo da porche Cayenne 2012");
        data.setAmount(new BigDecimal("2445.99"));
        CompleteResponseExemple res = new CompleteResponseExemple();
        res.setStatusCode(200);
        res.setMessage("OK");
        res.setData(data);

        String resJson = parser.parseResponse(res);
        System.out.println(resJson);

        assertEquals("{\"statusCode\":200,\"message\":\"OK\",\"data\":{\"description\":\"Amortecedor dianteiro esquerdo da porche Cayenne 2012\",\"amount\":2445.99}}", resJson);
    }

    @Test
    void parseResponseScenarioInfo() throws JsonProcessingException, ProtocolException, ServiceException {
        String jsonRequest = "{\"action\":\"P_INFO\",\"id\":1}";
        Request req = parser.parseRequest(jsonRequest, Request.class);

        ValidateRequest validator = new ValidateRequest();
        validator.validateRequest(req);

        ProductRepository repo = new ProductRepository();
        StokService service = new StokService(repo);

        Object serviceResult = service.handleRequest(req);

        Response res = Response.success(req.getAction(), serviceResult);

        System.out.println(res.getData());
        String resJson = parser.parseResponse(res);
        System.out.println(resJson);

//        assertEquals("{\"statusCode\":200,\"message\":\"OK\",\"data\":{\"description\":\"Amortecedor dianteiro esquerdo da porche Cayenne 2012\",\"amount\":2445.99}}", resJson);
    }
}