/*
 * This file is part of InjectionLib, https://github.com/Ceymikey/InjectionLib
 *
 * Copyright (c) 2024-2025 Ceymikey. All Rights Reserved.
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
package dev.ceymikey.injection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to build the embed.
 */
@Getter
public class EmbedBuilder {

    private final String authorName;
    private final String authorUrl;
    private final String authorIcon;
    private final String url;
    private final String title;
    private final String description;
    private final int color;
    private final List<Field> fields;
    private final String thumbnailUrl;
    private final String imageUrl;
    private final String footerText;
    private final String footerIconUrl;

    @Contract(pure = true)
    private EmbedBuilder(Construct construct) {
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
        private final List<Field> fields = new ArrayList<>();
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

        public Construct addField(String name, String value) {
            this.fields.add(new Field(name, value));
            return this;
        }

        public Construct setFooter(String footerText) {
            this.footerText = footerText;
            return this;
        }

        public Construct setFooterIcon(String footerIconUrl) {
            this.footerIconUrl = footerIconUrl;
            return this;
        }

        public EmbedBuilder build() {
            return new EmbedBuilder(this);
        }
    }

    @AllArgsConstructor
    public static class Field {
        public String name;
        public String value;
    }
}
