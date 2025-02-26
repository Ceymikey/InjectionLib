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
package dev.ceymikey.injection;

import dev.ceymikey.exceptions.FailedEndpointException;
import dev.ceymikey.exceptions.InjectionFailureException;
import dev.ceymikey.json.JsonArray;
import dev.ceymikey.json.JsonBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;

public class DiscordPayload {

    public static void inject(@NotNull EmbedBuilder builder) {
        if (builder.getUrl() == null || builder.getUrl().isEmpty()) {
            try {
                throw new FailedEndpointException();
            } catch (FailedEndpointException e) {
                throw new RuntimeException(e);
            }
        }

        if ((builder.getTitle() == null || builder.getTitle().isEmpty())
                && (builder.getDescription() == null || builder.getDescription().isEmpty())
                && (builder.getFields() == null || builder.getFields().isEmpty())) {
            try {
                throw new InjectionFailureException();
            } catch (InjectionFailureException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            JsonBuilder embed = new JsonBuilder();
            embed.put("title", builder.getTitle());
            embed.put("description", builder.getDescription());
            embed.put("color", builder.getColor());

            if (builder.getThumbnailUrl() != null && !builder.getThumbnailUrl().isEmpty()) {
                JsonBuilder thumbnail = new JsonBuilder();
                thumbnail.put("url", builder.getThumbnailUrl());
                embed.put("thumbnail", thumbnail);
            }

            JsonArray fieldsArray = new JsonArray();
            for (EmbedBuilder.Field field : builder.getFields()) {
                JsonBuilder fieldObject = new JsonBuilder();
                fieldObject.put("name", field.name);
                fieldObject.put("value", field.value);
                fieldsArray.put(fieldObject);
            }
            embed.put("fields", fieldsArray);

            // Add footer if available
            if (builder.getFooterText() != null && !builder.getFooterText().isEmpty()) {
                JsonBuilder footer = new JsonBuilder();
                footer.put("text", builder.getFooterText());
                embed.put("footer", footer);
            }

            JsonBuilder payload = new JsonBuilder();
            JsonArray embedsArray = new JsonArray();
            embedsArray.put(embed);
            payload.put("embeds", embedsArray);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(builder.getUrl());
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(new StringEntity(payload.toString()));

            httpClient.execute(httpPost);
            httpClient.close();
        } catch (Exception e) {
            System.out.println("INJECTION FAILURE! | " + e.getMessage());
            e.printStackTrace();
        }
    }
}