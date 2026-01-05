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
package dev.ceymikey.toolchain.rpc;

import dev.ceymikey.toolchain.rpc.payloads.Message;
import dev.ceymikey.toolchain.rpc.net.DiscordPayload;
import dev.ceymikey.toolchain.rpc.payloads.Embed;

/**
 * For now the main class just contains an example of the usage.
 */
public class Main {
    public static void main(String[] args) {
        /* This builds the embed data */
        Embed builder = new Embed.Construct()
                .setUrl("https://discord.com/api/webhooks/1236429617158553723/WCsAqaHic6XbMcDpN5g32mzdyMQEr-Yw4Dh6LOR592TVOL9OOyLN9c69URuWZ7GHvi2i")
                .setAuthor("Small text at the top of the embed")
                .setTitle("This is a test embed title!")
                .addField("33", "3", false)
                .addField("33", "3", true)
                .setDescription("This is a test embed description!")
                .setColor(12370112)
                .setFooter("Small text at the bottom of the embed")
                .build();

        /* This build a plain message */
        Message message = new Message("Welcome from java!", "https://discord.com/api/webhooks/1236429617158553723/WCsAqaHic6XbMcDpN5g32mzdyMQEr-Yw4Dh6LOR592TVOL9OOyLN9c69URuWZ7GHvi2i");

        /* This sends the actual payload */
        /* This gives you the freedom to build the payload early */
        /* and send it later on in your code whenever you want */
        DiscordPayload.inject(builder);

        /* You can also do this for any other payload like a message */
        DiscordPayload.inject(message);

        System.out.println("Successfully sent data!");
    }
}