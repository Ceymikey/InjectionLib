/*
 * This file is part of InjectionLib, https://github.com/Ceymikey/InjectionLib
 *
 * Copyright (c) 2024-2025 Ceymikey. All Rights Reserved.
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
package dev.ceymikey;

import dev.ceymikey.injection.DiscordPayload;
import dev.ceymikey.injection.EmbedBuilder;
import dev.ceymikey.injection.MessageBuilder;

/**
 * For now the main class just contains an example of the usage.
 */
public class Main {
    public static void main(String[] args) {
        /* This builds the embeds data */
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("EMBED_URL")
                .setTitle("This is a test embed title!")
                .setDescription("This is a test embed description!")
                .setColor(12370112)
                .setFooter("Small text at the bottom of the embed")
                .build();
        /* This sends the actual embed */
        /* This gives you the freedom to build the embed early */
        /* and send it later on in your code whenever you want */
        DiscordPayload.inject(builder);

        /* This sends a plain message text */
        MessageBuilder.sendPlain("EMBED_URL", "Cool message");

        System.out.println("Injection worked successfully!");
    }
}