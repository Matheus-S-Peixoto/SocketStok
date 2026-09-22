package org.stok.Protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class ProtocolParser {
    private static final ObjectMapper objMapper = new ObjectMapper();

    static {
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public static JsonNode jsonToNode(String req) throws JsonProcessingException {
        return objMapper.readTree(req);
    }

    public static <A> A nodeToClass(JsonNode node, Class<A> req) throws JsonProcessingException {
        return objMapper.treeToValue(node, req);
    }

    public static JsonNode classToNode(Object res) {
        return objMapper.valueToTree(res);
    }

    public static String nodeToJson(JsonNode node) throws JsonProcessingException {
        ObjectWriter objWritter = objMapper.writer();
//        objWritter = objWritter.with(SerializationFeature.INDENT_OUTPUT);
        return objWritter.writeValueAsString(node);
    }
}
