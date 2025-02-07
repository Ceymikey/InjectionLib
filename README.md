![License](https://img.shields.io/github/license/Ceymikey/InjectionLib?color=blue)
![Contributors](https://img.shields.io/github/contributors/Ceymikey/InjectionLib?color=blue)
![Issues](https://img.shields.io/github/issues/Ceymikey/InjectionLib?color=blue)
[![Discord](https://img.shields.io/badge/Discord-Click%20to%20Join-5865F2?logo=discord&logoColor=white)](https://discord.gg/MPuQEeZB4w)

# Discord webhooks java

### Implementing
How to add the library to your code. Its available for both maven and gradle
#### Gradle implementation
```gradle
repositories {
    maven { url "https://repo.ceymikey.dev/releases" }
}

dependencies {
    implementation "dev.ceymikey:injectionlib:{VERSION}"
}
```
#### Maven implementation
```xml
<repositories>
    <repository>
        <id>ceymikey-releases</id>
        <url>https://repo.ceymikey.dev/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.ceymikey</groupId>
        <artifactId>injectionlib</artifactId>
        <version>{VERSION}</version>
    </dependency>
</dependencies>

```

### Basic usage
Basic usage of the library in your projects code. The library provides you an option to build
an embed using the `EmbedBuilder` class and choose when to send it using the `inject` method call.
```java
package com.example.test;

import dev.ceymikey.injection.EmbedBuilder;
import dev.ceymikey.injection.DiscordPayload;

public class TestClass {
    public void testMethod() {
        /* This builds the embeds data */
        EmbedBuilder builder = new EmbedBuilder.Construct()
                .setUrl("EMBED_URL")
                .setTitle("This is a test embed title!")
                .setDescription("This is a test embed description!")
                .setColor(12370112) // Gray color
                .build();
        /* This sends the actual embed */
        /* This gives you the freedom to build the embed early */
        /* and send it later on in your code whenever you want */
        DiscordPayload.inject(builder);
    }
}
```
