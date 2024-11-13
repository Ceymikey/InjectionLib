package dev.ceymikey.injection;

import dev.ceymikey.exceptions.FailedEndpointException;
import dev.ceymikey.exceptions.InjectionFailureException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class DiscordPayload {

    public static void inject(EmbedBuilder builder) {
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
            JSONObject embed = new JSONObject();
            embed.put("title", builder.getTitle());
            embed.put("description", builder.getDescription());
            embed.put("color", builder.getColor());

            if (builder.getThumbnailUrl() != null && !builder.getThumbnailUrl().isEmpty()) {
                JSONObject thumbnail = new JSONObject();
                thumbnail.put("url", builder.getThumbnailUrl());
                embed.put("thumbnail", thumbnail);
            }

            JSONArray fieldsArray = new JSONArray();
            for (EmbedBuilder.Field field : builder.getFields()) {
                JSONObject fieldObject = new JSONObject();
                fieldObject.put("name", field.name);
                fieldObject.put("value", field.value);
                fieldsArray.put(fieldObject);
            }
            embed.put("fields", fieldsArray);

            JSONObject payload = new JSONObject();
            payload.put("embeds", new JSONArray().put(embed));

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
