/*
 * Copyright 2024 Ceymikey. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package injection;

import dev.ceymikey.exceptions.FailedEndpointException;
import dev.ceymikey.exceptions.InjectionFailureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import dev.ceymikey.injection.EmbedBuilder;
import dev.ceymikey.injection.DiscordPayload;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscordPayloadTest {

    @Test
    void testFailedEndpoint() {
        // Create builder with empty URL
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .build();

        // Verify that the correct exception is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DiscordPayload.inject(builder);
        });

        assertInstanceOf(FailedEndpointException.class, exception.getCause());
    }

    @Test
    void testEmptyContent() {
        // Create builder with URL but empty content
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/example")
                .build();

        // Verify that the correct exception is thrown
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DiscordPayload.inject(builder);
        });

        assertInstanceOf(InjectionFailureException.class, exception.getCause());
    }

    @Test
    void testSuccessfulInjection() throws Exception {
        // Create a complete builder
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/example")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .setColor(12370112)
                .addField("Field Name", "Field Value")
                .setThumbnailUrl("https://example.com/thumbnail.png")
                .setFooter("Test Footer")
                .build();

        // Create mocks for HTTP client
        CloseableHttpClient mockClient = mock(CloseableHttpClient.class);
        try (MockedStatic<HttpClients> httpClientsMock = Mockito.mockStatic(HttpClients.class)) {
            httpClientsMock.when(HttpClients::createDefault).thenReturn(mockClient);

            // Capture the HTTP POST request
            ArgumentCaptor<HttpPost> httpPostCaptor = ArgumentCaptor.forClass(HttpPost.class);

            // Execute the inject method
            DiscordPayload.inject(builder);

            // Verify HTTP client was called with the expected request
            verify(mockClient).execute(httpPostCaptor.capture());
            verify(mockClient).close();

            // Verify the HTTP POST request was configured correctly
            HttpPost capturedPost = httpPostCaptor.getValue();
            assertEquals("https://discord.com/api/webhooks/example", capturedPost.getURI().toString());
            assertEquals("application/json", capturedPost.getFirstHeader("content-type").getValue());

            // Get the request entity and verify it contains expected JSON
            StringEntity entity = (StringEntity) capturedPost.getEntity();
            String jsonPayload = convertEntityToString(entity);

            // Check for all the expected components in the JSON
            assertTrue(jsonPayload.contains("\"title\":\"Test Title\""));
            assertTrue(jsonPayload.contains("\"description\":\"Test Description\""));
            assertTrue(jsonPayload.contains("\"color\":12370112"));
            assertTrue(jsonPayload.contains("\"name\":\"Field Name\""));
            assertTrue(jsonPayload.contains("\"value\":\"Field Value\""));
            assertTrue(jsonPayload.contains("\"url\":\"https://example.com/thumbnail.png\""));
            assertTrue(jsonPayload.contains("\"text\":\"Test Footer\""));
        }
    }

    // Helper method to convert StringEntity to String for verification
    private String convertEntityToString(StringEntity entity) {
        try {
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            entity.writeTo(out);
            return out.toString();
        } catch (Exception e) {
            return "";
        }
    }
}