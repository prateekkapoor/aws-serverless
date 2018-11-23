package com.serverless.demo.endpoint.lex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.demo.model.lex.LexResponse;


/**
 *
 */
public class LexBookTicket implements RequestHandler<Map<String, Object>, Object> {


    private static final Logger LOG = Logger.getLogger(RequestHandler.class);

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received input: " + input);
        LOG.info("context: " + input);
        Map<String, Object> jsonRequest = null;
        try {
            String actionsResponseBody = (String) input.get("currentIntent");
            jsonRequest = parseContent(actionsResponseBody);
            LOG.info("Action Response " + actionsResponseBody);
        } catch (Throwable th) {
            LOG.error("Error occured while processing the event", th);
        }

        return getResponseMap(getResponse());
    }


    private Map<String, Object> parseContent(String body) throws IOException, JsonParseException,
            JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        Map<String, Object> responseMap = mapper.readValue(body, new TypeReference<Map<String, Object>>() {
        });
        return responseMap;
    }

    private Map<String, Object> getResponseMap(LexResponse lexResponse) {
        Map<String, Object> lexResponseMap = new HashMap<>();
        lexResponseMap.put("dialogAction", lexResponse.getDialogAction());
        return lexResponseMap;

    }

    private LexResponse getResponse() {
        return new LexResponse(new LexResponse.DialogAction(new LexResponse.Message(String.format(" Booking is confirmed. Booking Id %s", UUID.randomUUID().toString()))));
    }

    private String getResponseAsString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(getResponse());
        } catch (JsonProcessingException e) {
            LOG.error("failed to serialize object", e);
            throw new RuntimeException(e);
        }
    }
}
