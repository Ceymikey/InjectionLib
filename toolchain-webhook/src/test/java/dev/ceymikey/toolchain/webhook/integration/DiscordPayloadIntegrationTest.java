/*
 * This file is part of discord-toolchain, https://github.com/Ceymikey/discord-toolchain
 *
 * Copyright (c) 2024-2026 Ceymikey and contributors. All Rights Reserved.
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
 *
 */
package dev.ceymikey.toolchain.webhook.integration;

import dev.ceymikey.toolchain.webhook.net.DiscordPayload;
import dev.ceymikey.net.EmbedBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

/**
 * Integration tests for the Discord webhook functionality.
 * These tests will only run if the DISCORD_WEBHOOK_URL environment variable is set.
 */
@Tag("integration")
class DiscordPayloadIntegrationTest {

    @Test
    @EnabledIfEnvironmentVariable(named = "DISCORD_WEBHOOK_URL", matches = ".*")
    void testSendWebhook() {
        // Get the webhook URL from environment variables
        String webhookUrl = System.getenv("DISCORD_WEBHOOK_URL");

        // Create a test embed
        EmbedBuilder embed = new EmbedBuilder.Construct()
                .setUrl(webhookUrl)
                .setTitle("Integration Test")
                .setDescription("This is an automated test of the Discord webhook integration.")
                .setColor(3447003) // Discord blue color
                .addField("Test Field", "This is a test field")
                .addField("Build", "Gradle Build " + System.currentTimeMillis())
                .setFooter("Test completed at " + java.time.LocalDateTime.now())
                .build();

        // Send the webhook
        DiscordPayload.inject(embed);

        // If we reach this point without exception, the test passes
        // We don't have a way to verify the webhook was actually received by Discord
    }
}