package dev.ceymikey;

import dev.ceymikey.injection.EmbedBuilder;
import dev.ceymikey.injection.DiscordPayload;

/**
 * For now the main class just contains an example of the usage.
 * @author Ceymikey
 */
public class Main {
    public static void main(String[] args) {
        /* This builds the embeds data */
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("https://discord.com/api/webhooks/1301990321328099378/1KKQJhvLE6PUa0qJVdELiv-FpuJA3fH4wiqmn3dxzK4JTi5T8JuIxmufBGn8w40NSKfI")
                .setTitle("This is a test embed title!")
                .setDescription("This is a test embed description!")
                .setColor(12370112)
                .build();
        /* This sends the actual embed */
        /* This gives you the freedom to build the embed early */
        /* and send it later on in your code whenever you want */
        DiscordPayload.inject(builder);

        System.out.println("Injection worked successfully!");
    }
}