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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the JsonElement class, focusing on serializeValue functionality
 * for Maps, Lists, and other types not covered in JsonArray and JsonObject tests.
 */
class JsonElementTest {

    @Test
    void testSerializeMap() {
        TestJsonElement element = new TestJsonElement();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", 42);
        map.put("key3", true);

        String result = element.testSerializeValue(map);
        assertEquals("{\"key1\":\"value1\",\"key2\":42,\"key3\":true}", result);
    }

    @Test
    void testSerializeNestedMap() {
        TestJsonElement element = new TestJsonElement();
        Map<String, Object> innerMap = new LinkedHashMap<>();
        innerMap.put("inner1", "value");
        innerMap.put("inner2", 99);

        Map<String, Object> outerMap = new LinkedHashMap<>();
        outerMap.put("outer1", "string");
        outerMap.put("nested", innerMap);

        String result = element.testSerializeValue(outerMap);
        assertEquals("{\"outer1\":\"string\",\"nested\":{\"inner1\":\"value\",\"inner2\":99}}", result);
    }

    @Test
    void testSerializeList() {
        TestJsonElement element = new TestJsonElement();
        List<Object> list = new ArrayList<>();
        list.add("string");
        list.add(123);
        list.add(true);

        String result = element.testSerializeValue(list);
        assertEquals("[\"string\",123,true]", result);
    }

    @Test
    void testSerializeNestedList() {
        TestJsonElement element = new TestJsonElement();
        List<Object> innerList = new ArrayList<>();
        innerList.add("a");
        innerList.add("b");

        List<Object> outerList = new ArrayList<>();
        outerList.add("outer");
        outerList.add(innerList);

        String result = element.testSerializeValue(outerList);
        assertEquals("[\"outer\",[\"a\",\"b\"]]", result);
    }

    @Test
    void testSerializeListWithMap() {
        TestJsonElement element = new TestJsonElement();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("key", "value");

        List<Object> list = new ArrayList<>();
        list.add("string");
        list.add(map);

        String result = element.testSerializeValue(list);
        assertEquals("[\"string\",{\"key\":\"value\"}]", result);
    }

    @Test
    void testSerializeMapWithList() {
        TestJsonElement element = new TestJsonElement();
        List<Object> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("items", list);

        String result = element.testSerializeValue(map);
        assertEquals("{\"items\":[\"item1\",\"item2\"]}", result);
    }

    @Test
    void testSerializeCustomObject() {
        TestJsonElement element = new TestJsonElement();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        String result = element.testSerializeValue(calendar);
        // Custom objects should be serialized as strings using toString()
        assertEquals("\"" + calendar.toString().replace("\"", "\\\"") + "\"", result);
    }

    @Test
    void testSerializeEnum() {
        TestJsonElement element = new TestJsonElement();
        TimeUnit unit = TimeUnit.DAYS;

        String result = element.testSerializeValue(unit);
        assertEquals("\"DAYS\"", result);
    }

    // An enum for testing
    private enum TimeUnit {
        SECONDS, MINUTES, HOURS, DAYS
    }

    // Test helper class to expose protected methods for testing
    private static class TestJsonElement extends JsonElement {
        public String testSerializeValue(Object value) {
            return serializeValue(value);
        }

        public String testSerializeObject(Map<String, Object> map) {
            return serializeObject(map);
        }

        public String testSerializeArray(List<Object> list) {
            return serializeArray(list);
        }
    }
}