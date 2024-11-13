### Implementing
```kotlin
repositories {
    maven { url "https://repo.ceymikey.dev/releases" }
}

dependencies {
    implementation "dev.ceymikey:injectionlib:{VERSION}"
}
```

### Basic usage
```java
package com.example.test;

import dev.ceymikey.injection.EmbedBuilder;
import dev.ceymikey.injection.DiscordPayload;

public class TestClass {
    public static void testMethod(String[] args) {
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