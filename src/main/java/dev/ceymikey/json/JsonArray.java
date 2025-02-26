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
package dev.ceymikey.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple JSON array builder
 *
 * @author svaningelgem
 */
public class JsonArray {
    private final List<Object> items;

    public JsonArray() {
        this.items = new ArrayList<>();
    }

    public JsonArray put(Object value) {
        this.items.add(value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;
        for (Object item : items) {
            if (!first) {
                sb.append(",");
            }
            first = false;

            sb.append(serializeValue(item));
        }

        sb.append("]");
        return sb.toString();
    }

    private String serializeValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + escapeString((String) value) + "\"";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof JsonBuilder) {
            return value.toString();
        } else if (value instanceof JsonArray) {
            return value.toString();
        } else if (value instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) value;
            return serializeArray(list);
        } else {
            return "\"" + escapeString(value.toString()) + "\"";
        }
    }

    private String serializeArray(List<Object> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;
        for (Object item : list) {
            if (!first) {
                sb.append(",");
            }
            first = false;

            sb.append(serializeValue(item));
        }

        sb.append("]");
        return sb.toString();
    }

    private String escapeString(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}