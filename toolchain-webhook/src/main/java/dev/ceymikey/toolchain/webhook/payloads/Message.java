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
package dev.ceymikey.toolchain.webhook.payloads;

import dev.ceymikey.toolchain.webhook.json.JsonObject;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Message extends Payload {
    private static String text;

    public Message(@NonNull String text, @NonNull String url) {
        super(url);
        this.text = text;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.put("content", text);
        return json;
    }
}
