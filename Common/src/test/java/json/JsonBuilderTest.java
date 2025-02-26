/*
 * Copyright 2024 Ceymikey. All Rights Reserved.
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
package json;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonBuilderTest {

    @Test
    void testEmptyObject() {
        JsonBuilder builder = new JsonBuilder();
        assertEquals("{}", builder.toString());
    }

    @Test
    void testSimpleObject() {
        JsonBuilder builder = new JsonBuilder()
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
        JsonBuilder nested = new JsonBuilder()
                .put("key", "value");

        JsonBuilder builder = new JsonBuilder()
                .put("data", nested);

        assertEquals("{\"data\":{\"key\":\"value\"}}", builder.toString());
    }

    @Test
    void testWithArray() {
        JsonArray array = new JsonArray()
                .put("item1")
                .put("item2");

        JsonBuilder builder = new JsonBuilder()
                .put("items", array);

        assertEquals("{\"items\":[\"item1\",\"item2\"]}", builder.toString());
    }

    @Test
    void testEscapeCharacters() {
        JsonBuilder builder = new JsonBuilder()
                .put("text", "Line 1\nLine 2\tTabbed\r\n\"Quoted\"");

        String json = builder.toString();
        assertTrue(json.contains("\\n"));
        assertTrue(json.contains("\\t"));
        assertTrue(json.contains("\\r\\n"));
        assertTrue(json.contains("\\\""));
    }

    @Test
    void testNullValue() {
        JsonBuilder builder = new JsonBuilder()
                .put("nullValue", null);

        assertEquals("{\"nullValue\":null}", builder.toString());
    }
}