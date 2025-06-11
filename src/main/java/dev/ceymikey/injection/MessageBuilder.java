package dev.ceymikey.injection;

import dev.ceymikey.json.JsonObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Getter
public class MessageBuilder {
    /**
     * Sends a plain text message via the webhook.
     * @param url       The webhook URL.
     * @param content   The content of the message.
     */
    public static void sendPlain(@NotNull String url, @NotNull String content) {
        if (url == null || content == null) {
            throw new IllegalArgumentException("Webhook URL and content must not be null.");
        }

        JsonObject json = new JsonObject();
        json.put("content", content);

        try {
            DiscordPayload.sendWebhook(url, json);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send plain message", e);
        }
    }
}
