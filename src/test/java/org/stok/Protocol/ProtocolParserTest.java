package org.stok.Protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.stok.Protocol.pojo.RequestExemple;
import org.stok.Protocol.pojo.ResponseExemple;
import org.studies.Protocol;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProtocolParserTest {
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
        JsonNode node = ProtocolParser.jsonToNode(requestExemple1);

        assertEquals("P_CREATE", node.get("action").asText());
    }

    @Test
    void simpleJSONscenarioParsingNodeToClass() throws JsonProcessingException {
        JsonNode node = ProtocolParser.jsonToNode(requestExemple1);
        RequestExemple req = ProtocolParser.nodeToClass(node, RequestExemple.class);

        assertEquals(Actions.P_CREATE, req.getAction());
    }

    @Test
    void simpleJSONscenarioParsingClassToNode() {
        ResponseExemple res = new ResponseExemple();
        res.setStatusCode(200);
        res.setMessage("OK");
        JsonNode node = ProtocolParser.classToNode(res);

        assertEquals(res.getStatusCode(), node.get("statusCode").asInt());
        assertEquals(res.getMessage(), node.get("message").asText());
    }

    @Test
    void simpleJSONscenarioParsingNodeToJson() throws JsonProcessingException {
        ResponseExemple res = new ResponseExemple();
        res.setStatusCode(200);
        res.setMessage("OK");
        JsonNode node = ProtocolParser.classToNode(res);

        String json = ProtocolParser.nodeToJson(node);

        System.out.println(json);
    }

    @Test
    void ComplexJSONscenarioParsingJsonToNode() throws JsonProcessingException {
        JsonNode node = ProtocolParser.jsonToNode(requestExemple2);

        System.out.println(node);
        assertEquals("P_CREATE", node.get("action").asText());
    }

    @Test
    void ComlpexJSONscenarioParsingNodeToClass() throws JsonProcessingException {
        JsonNode node = ProtocolParser.jsonToNode(requestExemple2);
        RequestExemple req = ProtocolParser.nodeToClass(node, RequestExemple.class);

        assertEquals(Actions.P_CREATE, req.getAction());
        assertNull(req.getId());
        assertEquals("Amortecedor Bosche Porche Cayenne", req.getBody().getName());
        assertEquals("Amortecedor dianteiro esquerdo da porche Cayenne 2013 importada", req.getBody().getDescription());
        assertEquals(new BigDecimal("3050.43"), req.getBody().getAmount());
        assertEquals("PCAUT0000000051", req.getBody().getCode());
    }
}