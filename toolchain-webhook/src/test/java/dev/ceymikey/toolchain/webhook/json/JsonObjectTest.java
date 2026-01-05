/*
 * This file is part of discord-toolchain, https://github.com/Ceymikey/discord-toolchain
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
 *
 */
package dev.ceymikey.toolchain.webhook.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonObjectTest {

    @Test
    void testEmptyObject() {
        JsonObject builder = new JsonObject();
        assertEquals("{}", builder.toString());
    }

    @Test
    void testSimpleObject() {
        JsonObject builder = new JsonObject()
                .put("name", "Test")
                .put("value", 42)
                .put("active", true);

        String json = builder.toString();
        assertTrue(json.contains("\"name\":\"Test\""));
        assertTrue(json.contains("\"value\":42"));
        assertTrue(json.contains("\"active\":true"));
    }

    @Test
    void testNestedObject() {
        JsonObject nested = new JsonObject()
                .put("key", "value");

        JsonObject builder = new JsonObject()
                .put("data", nested);

        assertEquals("{\"data\":{\"key\":\"value\"}}", builder.toString());
    }

    @Test
    void testWithArray() {
        JsonArray array = new JsonArray()
                .put("item1")
                .put("item2");

        JsonObject builder = new JsonObject()
                .put("items", array);

        assertEquals("{\"items\":[\"item1\",\"item2\"]}", builder.toString());
    }

    @Test
    void testEscapeCharacters() {
        JsonObject builder = new JsonObject()
                .put("text", "Line 1\nLine 2\tTabbed\r\n\"Quoted\"");

        String json = builder.toString();
        assertTrue(json.contains("\\n"));
        assertTrue(json.contains("\\t"));
        assertTrue(json.contains("\\r\\n"));
        assertTrue(json.contains("\\\""));
    }

    @Test
    void testNullValue() {
        JsonObject builder = new JsonObject()
                .put("nullValue", null);

        assertEquals("{\"nullValue\":null}", builder.toString());
    }
}