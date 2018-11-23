package com.serverless.demo.endpoint;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.demo.model.ApiGatewayResponse;
import com.serverless.demo.model.BookingDetails;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class GetBookingDetails implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private static final Logger LOG = Logger.getLogger(RequestHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received input: " + input);
        LOG.info("context: " + input);
        ApiGatewayResponse.Builder responseBuilder = ApiGatewayResponse.builder().setStatusCode(HttpStatus.SC_OK)
                .setObjectBody(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "application/json")).setObjectBody(
                        new BookingDetails("123", "Hyderabad"));
        return responseBuilder.build();
    }

    /**
     *
     */
    public static class LexBookTicket implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {


        private static final Logger LOG = Logger.getLogger(RequestHandler.class);

        @Override
        public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
            LOG.info("received input: " + input);
            LOG.info("context: " + input);
            Map<String, Object> jsonRequest = null;
            try {
                String actionsResponseBody = (String) input.get("body");
                jsonRequest = parseContent(actionsResponseBody);
                LOG.info("Action Response " + actionsResponseBody);
            } catch (Throwable th) {
                LOG.error("Error occured while processing the event", th);
            }
            BookingDetails bookingDetails = new BookingDetails(UUID.randomUUID().toString(), (String) jsonRequest.get("city"));
            ApiGatewayResponse.Builder responseBuilder = ApiGatewayResponse.builder().setStatusCode(HttpStatus.SC_OK)
                    .setObjectBody(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "application/json"))
                    .setObjectBody(bookingDetails);
            return responseBuilder.build();
        }


        private Map<String, Object> parseContent(String body) throws IOException, JsonParseException,
                JsonMappingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            Map<String, Object> responseMap = mapper.readValue(body, new TypeReference<Map<String, Object>>() {
            });
            return responseMap;
        }
    }
}
