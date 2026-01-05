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
package dev.ceymikey.toolchain.rpc.payloads;

import dev.ceymikey.toolchain.rpc.exceptions.FailedEndpointException;
import dev.ceymikey.toolchain.rpc.exceptions.InjectionFailureException;
import dev.ceymikey.toolchain.rpc.json.JsonArray;
import dev.ceymikey.toolchain.rpc.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Embed extends Payload {
    private final String authorName;
    private final String authorUrl;
    private final String authorIcon;
    private final String url;
    private final String title;
    private final String description;
    private final int color;
    private final List<Construct.Field> fields;
    private final String thumbnailUrl;
    private final String imageUrl;
    private final String footerText;
    private final String footerIconUrl;

    @Contract(pure = true)
    private Embed(Construct construct) {
        super(construct.url);
        this.authorName = construct.authorName;
        this.authorUrl = construct.authorUrl;
        this.authorIcon = construct.authorIcon;
        this.url = construct.url;
        this.title = construct.title;
        this.description = construct.description;
        this.color = construct.color;
        this.fields = construct.fields;
        this.thumbnailUrl = construct.thumbnailUrl;
        this.imageUrl = construct.imageUrl;
        this.footerText = construct.footerText;
        this.footerIconUrl = construct.footerIconUrl;
    }

    public static class Construct {
        private final List<Construct.Field> fields = new ArrayList<>();
        private String authorName;
        private String authorUrl;
        private String authorIcon;
        private String url;
        private String title;
        private String description;
        private int color;
        private String thumbnailUrl;
        private String imageUrl;
        private String footerText;
        private String footerIconUrl;

        public Construct setAuthor(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Construct setAuthor(String authorName, String authorIcon) {
            this.authorName = authorName;
            this.authorIcon = authorIcon;
            return this;
        }

        public Construct setAuthor(String authorName, String authorUrl, String authorIcon) {
            this.authorName = authorName;
            this.authorUrl = authorUrl;
            this.authorIcon = authorIcon;
            return this;
        }

        public Construct setUrl(String url) {
            this.url = url;
            return this;
        }

        public Construct setTitle(String title) {
            this.title = title;
            return this;
        }

        public Construct setDescription(String description) {
            this.description = description;
            return this;
        }

        public Construct setColor(int color) {
            this.color = color;
            return this;
        }

        public Construct setThumbnail(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Construct setImage(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Construct addField(@NonNull String name, @NonNull String value, boolean inline) {
            this.fields.add(new Construct.Field(name, value, inline));
            return this;
        }

        public Construct setFooter(String footerText) {
            this.footerText = footerText;
            return this;
        }

        public Construct setFooter(String footerText, String footerIconUrl) {
            this.footerText = footerText;
            this.footerIconUrl = footerIconUrl;
            return this;
        }

        public Embed build() {
            if (url == null || url.isEmpty()) {
                throw new FailedEndpointException();
            }

            if ((title == null || title.isEmpty())
                    && (description == null || description.isEmpty())
                    && (fields == null || fields.isEmpty())) {
                throw new InjectionFailureException();
            }
            return new Embed(this);
        }

        @AllArgsConstructor
        public static class Field {
            public String name;
            public String value;
            public boolean inline;
        }
    }

    @Override
    public JsonObject serialize() {
        JsonObject embed = new JsonObject();
        embed.put("title", getTitle());
        embed.put("description", getDescription());
        embed.put("color", getColor());

        if (getThumbnailUrl() != null && !getThumbnailUrl().isEmpty()) {
            JsonObject thumbnail = new JsonObject();
            thumbnail.put("url", getThumbnailUrl());
            embed.put("thumbnail", thumbnail);
        }

        if (getAuthorName() != null && !getAuthorName().isEmpty()) {
            JsonObject author = new JsonObject();
            author.put("name", getAuthorName());
            if (getAuthorUrl() != null) author.put("url", getAuthorUrl());
            if (getAuthorIcon() != null) author.put("icon_url", getAuthorIcon());
            embed.put("author", author);
        }

        JsonArray fieldsArray = new JsonArray();
        for (Construct.Field field : getFields()) {
            JsonObject fieldObject = new JsonObject();
            fieldObject.put("name", field.name);
            fieldObject.put("value", field.value);
            fieldObject.put("inline", field.inline);
            fieldsArray.put(fieldObject);
        }
        embed.put("fields", fieldsArray);

        if (getImageUrl() != null && !getImageUrl().isEmpty()) {
            JsonObject image = new JsonObject();
            image.put("url", getImageUrl());
            embed.put("image", image);
        }

        if (getFooterText() != null && !getFooterText().isEmpty()) {
            JsonObject footer = new JsonObject();
            footer.put("text", getFooterText());
            if (getFooterIconUrl() != null && !getFooterIconUrl().isEmpty()) {
                footer.put("icon_url", getFooterIconUrl());
            }
            embed.put("footer", footer);
        }

        JsonObject payload = new JsonObject();
        payload.put("embeds", new JsonArray(embed));
        return payload;
    }
}
