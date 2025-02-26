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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple JSON object builder
 *
 * @author svaningelgem
 */
public class JsonBuilder extends JsonElement {
    private final Map<String, Object> data;

    public JsonBuilder() {
        this.data = new LinkedHashMap<>();
    }

    public JsonBuilder put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return serializeObject(this.data);
    }

}