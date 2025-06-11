/*
 * This file is part of InjectionLib, https://github.com/Ceymikey/InjectionLib
 *
 * Copyright (c) 2025 svaningelgem. All Rights Reserved.
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
package dev.ceymikey.injection;

import dev.ceymikey.exceptions.FailedEndpointException;
import dev.ceymikey.exceptions.InjectionFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the DiscordPayload class using a custom URLStreamHandler
 * instead of MockedConstruction to avoid class loading issues.
 */
@ExtendWith(MockitoExtension.class)
class DiscordPayloadTest {

    // Register our custom handler
    private void setupURLStreamHandlerFactory(HttpURLConnection mockConnection) {
        try {
            // Create our test handler
            TestURLStreamHandler handler = new TestURLStreamHandler(mockConnection);

            // Create a URLStreamHandlerFactory that returns our test handler
            URLStreamHandlerFactory factory = protocol -> {
                if ("https".equals(protocol)) {
                    return handler;
                }
                return null;
            };

            // Reset the URLStreamHandlerFactory field to allow setting it again
            Field factoryField = URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            factoryField.set(null, null);

            // Set our factory
            URL.setURLStreamHandlerFactory(factory);
        } catch (Exception e) {
            // If we can't set the factory (e.g., it was already set),
            // we'll use reflection to modify the handlers table directly
            try {
                Field handlersField = URL.class.getDeclaredField("handlers");
                handlersField.setAccessible(true);

                @SuppressWarnings("unchecked")
                java.util.Hashtable<String, URLStreamHandler> handlers =
                        (java.util.Hashtable<String, URLStreamHandler>) handlersField.get(null);

                handlers.put("https", new TestURLStreamHandler(mockConnection));
            } catch (Exception ex) {
                throw new RuntimeException("Failed to set up URL handler", ex);
            }
        }
    }

    @BeforeEach
    void setUp() {
        // Ensure JsonObject class is loaded
        try {
            Class.forName("dev.ceymikey.json.JsonObject");
        } catch (ClassNotFoundException e) {
            fail("JsonObject class not found: " + e.getMessage());
        }
    }

    @Test
    void testFailedEndpoint() {
        // Create builder with empty URL
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("")
                .setTitle("Test Title")
                .setDescription("Test Description")
                .build();

        // Verify that the correct exception is thrown
        Exception exception = assertThrows(FailedEndpointException.class, () -> {
            DiscordPayload.inject(builder);
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    void testEmptyContent() {
        // Create builder with URL but empty content
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/example")
                .build();

        // Verify that the correct exception is thrown
        Exception exception = assertThrows(InjectionFailureException.class, () -> {
            DiscordPayload.inject(builder);
        });

        assertNotNull(exception.getMessage());
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
                .setThumbnail("https://example.com/thumbnail.png")
                .setFooter("Test Footer")
                .build();

        // Create ByteArrayOutputStream to capture JSON payload
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create and configure our mock connection
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);

        // Setup the output stream to capture the JSON payload
        when(mockConnection.getOutputStream()).thenReturn(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                outputStream.write(b, off, len);
            }
        });

        when(mockConnection.getResponseCode()).thenReturn(200);

        // Set up the URL handler to return our mock connection
        setupURLStreamHandlerFactory(mockConnection);

        // Execute the inject method
        DiscordPayload.inject(builder);

        // Verify that the mock connection methods were called
        verify(mockConnection).setRequestMethod("POST");
        verify(mockConnection).setRequestProperty("Content-Type", "application/json");
        verify(mockConnection).setDoOutput(true);
        verify(mockConnection).setFixedLengthStreamingMode(anyInt());
        verify(mockConnection).getOutputStream();
        verify(mockConnection).getResponseCode();
        verify(mockConnection).disconnect();

        // Get the captured JSON payload
        String jsonPayload = outputStream.toString("UTF-8");

        // Check for all the expected components in the JSON
        assertTrue(jsonPayload.contains("\"title\":\"Test Title\""));
        assertTrue(jsonPayload.contains("\"description\":\"Test Description\""));
        assertTrue(jsonPayload.contains("\"color\":12370112"));
        assertTrue(jsonPayload.contains("\"name\":\"Field Name\""));
        assertTrue(jsonPayload.contains("\"value\":\"Field Value\""));
        assertTrue(jsonPayload.contains("\"url\":\"https://example.com/thumbnail.png\""));
        assertTrue(jsonPayload.contains("\"text\":\"Test Footer\""));
    }

    @Test
    void testHttpErrorHandling() throws Exception {
        // Create a complete builder
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/example")
                .setTitle("Test Title")
                .build();

        // Create and configure our mock connection to simulate an error
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockConnection.getResponseCode()).thenReturn(400); // Bad request

        // Set up the URL handler to return our mock connection
        setupURLStreamHandlerFactory(mockConnection);

        // Execute the inject method - should not throw exception but log error
        DiscordPayload.inject(builder);

        // Verify error handling
        verify(mockConnection).getResponseCode();
        verify(mockConnection).disconnect();
    }

    @Test
    void testExceptionHandling() throws Exception {
        // Create a complete builder
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/example")
                .setTitle("Test Title")
                .build();

        // Create and configure our mock connection to throw an exception
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        when(mockConnection.getOutputStream()).thenThrow(new IOException("Test exception"));

        // Set up the URL handler to return our mock connection
        setupURLStreamHandlerFactory(mockConnection);

        // Execute the inject method - should handle exception
        DiscordPayload.inject(builder);

        // Method should handle the exception internally without propagating it
        verify(mockConnection).disconnect();
    }

    // Custom handler for URL connections
    private static class TestURLStreamHandler extends URLStreamHandler {
        private final HttpURLConnection mockConnection;

        public TestURLStreamHandler(HttpURLConnection mockConnection) {
            this.mockConnection = mockConnection;
        }

        @Override
        protected URLConnection openConnection(URL url) {
            return mockConnection;
        }
    }
}