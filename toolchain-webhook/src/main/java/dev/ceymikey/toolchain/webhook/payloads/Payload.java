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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The payload class is an abstract class that
 * represents any type of discord part that can be serialized
 * and sent in the stream like for example: embeds, polls, messages etc
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Payload {
    /**
     * The destination URL of the webhook
     * you want to sent the payload to.
     */
    private final String url;

    /**
     * The serialize method contains the logic
     * to turn the part data into json making it ready
     * to be sent to the discord API.
     */
    public abstract JsonObject serialize();
}
