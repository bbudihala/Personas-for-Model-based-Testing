package com.generated.feature.customer.profile.altering;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

/**
 * Tests the creation of a new customer using the /v2/customers API endpoint.
 */
public class CreateCustomer {
    private final String customersEndpoint = "https://connect.squareupsandbox.com/v2/customers";
    private final String bearerToken = "EAAAlzOz2w1iK0dAhEP6hx7uJU7xrKl8ZPvyiRyFG4JCS7WlM-6DaVF07k1flq5_";
    private Map<String, String> input;
    private final Map<String, String> output = new HashMap<String, String>();

    // Test specific objects
    private final Faker faker = new Faker();
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectNode requestBodyJson = objectMapper.createObjectNode();
    private HttpResponse<String> response = null;

    public void init(Supplier<Map<String, String>> inputSupplier) {
        input = java.util.Objects.requireNonNullElse(inputSupplier.get(), Map.of());
    }

    public void store(Consumer<Map<String, String>> outputConsumer) {
        outputConsumer.accept(output);
    }

    /**
     * Given
     * 
     */
    public void aCompanyNameAndEmailAddressIsProvided() {
        requestBodyJson.put("company_name", faker.company().name());
        requestBodyJson.put("email_address", faker.internet().emailAddress());
    }

    /**
     * When
     * 
     */
    public void aPostRequestToCreateCustomerIsSent() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(customersEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson.toString()))
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Then
     *
     */
    public void aCustomerIsSuccessfullyCreatedWithDetails() {
        assertThat(response.statusCode()).isEqualTo(200);
        JsonNode responseBodyJson = null;
        try {
            responseBodyJson = objectMapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
        assertThat(responseBodyJson.get("customer").get("company_name").asText())
                .isEqualTo(requestBodyJson.get("company_name").asText());
        assertThat(responseBodyJson.get("customer").get("email_address").asText())
                .isEqualTo(requestBodyJson.get("email_address").asText());
        assertThat(responseBodyJson.get("customer").get("id").asText()).isNotNull();

        output.put("customerId", responseBodyJson.get("customer").get("id").asText());
    }

    public void runScenario(Supplier<Map<String, String>> inputSupplier, Consumer<Map<String, String>> outputConsumer) {
        init(inputSupplier);
        aCompanyNameAndEmailAddressIsProvided();
        aPostRequestToCreateCustomerIsSent();
        aCustomerIsSuccessfullyCreatedWithDetails();
        store(outputConsumer);
    }
}