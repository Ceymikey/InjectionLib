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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple JSON object builder
<<<<<<< HEAD
=======
 *
 * @author svaningelgem
>>>>>>> 41c935fe528f3aed8fb34298b95a4e129f61583c
 */
public class JsonObject extends JsonElement {
    private final Map<String, Object> data = new LinkedHashMap<>();

    public JsonObject put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return serializeObject(this.data);
    }

}