/*
<<<<<<< HEAD
 * This file is part of InjectionLib, https://github.com/Ceymikey/InjectionLib
 *
 * Copyright (c) 2024-2025 Ceymikey. All Rights Reserved.
=======
 * Copyright 2025 svaningelgem. All Rights Reserved.
>>>>>>> 41c935fe528f3aed8fb34298b95a4e129f61583c
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
package dev.ceymikey.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

<<<<<<< HEAD
=======
/**
 * @author svaningelgem
 */
>>>>>>> 41c935fe528f3aed8fb34298b95a4e129f61583c
class JsonArrayTest {

    @Test
    void testEmptyArray() {
        JsonArray array = new JsonArray();
        assertEquals("[]", array.toString());
    }

    @Test
    void testSimpleArray() {
        JsonArray array = new JsonArray()
                .put("string")
                .put(123)
                .put(true);

        String json = array.toString();
        assertEquals("[\"string\",123,true]", json);
    }

    @Test
    void testNestedObjects() {
        JsonObject obj1 = new JsonObject().put("id", 1);
        JsonObject obj2 = new JsonObject().put("id", 2);

        JsonArray array = new JsonArray()
                .put(obj1)
                .put(obj2);

        assertEquals("[{\"id\":1},{\"id\":2}]", array.toString());
    }

    @Test
    void testNestedArrays() {
        JsonArray nested = new JsonArray()
                .put("a")
                .put("b");

        JsonArray array = new JsonArray()
                .put(nested);

        assertEquals("[[\"a\",\"b\"]]", array.toString());
    }

    @Test
    void testMixedTypes() {
        JsonArray array = new JsonArray()
                .put("string")
                .put(42)
                .put(new JsonObject().put("key", "value"))
                .put(new JsonArray().put(1).put(2));

        String json = array.toString();
        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));
        assertTrue(json.contains("\"string\""));
        assertTrue(json.contains("42"));
        assertTrue(json.contains("{\"key\":\"value\"}"));
        assertTrue(json.contains("[1,2]"));
    }

    @Test
    void testNullValue() {
        JsonArray array = new JsonArray()
                .put(null);

        assertEquals("[null]", array.toString());
    }
}