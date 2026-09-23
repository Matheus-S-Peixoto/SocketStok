package org.stok.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class ProtocolParser {
    private final ObjectMapper objMapper;

    public ProtocolParser() {
        this.objMapper = new ObjectMapper();

        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    protected JsonNode jsonToNode(String req) throws JsonProcessingException {
        return objMapper.readTree(req);
    }

    protected <A> A nodeToClass(JsonNode node, Class<A> req) throws JsonProcessingException {
        return objMapper.treeToValue(node, req);
    }

    protected JsonNode classToNode(Object res) {
        return objMapper.valueToTree(res);
    }

    protected String nodeToJson(JsonNode node) throws JsonProcessingException {
        ObjectWriter objWritter = objMapper.writer();
//        objWritter = objWritter.with(SerializationFeature.INDENT_OUTPUT);
        return objWritter.writeValueAsString(node);
    }

    public <A> A parseRequest(String reqJson, Class<A> reqClass) throws JsonProcessingException {
        JsonNode reqNode = jsonToNode(reqJson);
        return nodeToClass(reqNode, reqClass);
    }

    public String parseResponse(Object resObj) throws JsonProcessingException {
        JsonNode resNode = classToNode(resObj);
        return nodeToJson(resNode);
    }
}
