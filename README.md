![License](https://img.shields.io/github/license/Ceymikey/InjectionLib?color=blue)
![Contributors](https://img.shields.io/github/contributors/Ceymikey/InjectionLib?color=blue)
![Issues](https://img.shields.io/github/issues/Ceymikey/InjectionLib?color=blue)
[![Discord](https://img.shields.io/badge/Discord-Click%20to%20Join-5865F2?logo=discord&logoColor=white)](https://discord.gg/MPuQEeZB4w)

# Discord Toolchain
A set of tools made to interact with the discord API

> [!WARNING]
> This project is still in development and may be missing some key features.
> All other features should work.

### Features
- [x] Discord-Webhook
    - [x] Message sending
    - [x] Embed sending
    - [ ] Polls sending
    - [ ] Message components
- [ ] Discord-RPC
    - [ ] Maybe in the future

### Implementing
How to add the library to your project. Its available for both maven and gradle
#### Gradle implementation
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Ceymikey:discord-toolchain:{LATEST_VERSION}'
}
```
#### Maven implementation
```xml
<project>
    
    <repositories>
        <repository>
            <id>ceymikey-releases</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>com.github.Ceymikey</groupId>
            <artifactId>discord-toolchain</artifactId>
            <version>LATEST_VERSION</version>
        </dependency>
    </dependencies>

</project>
```

### Basic usage
Basic usage of the library in your projects code. The library provides you an option to build
an embed using the `Embed` class and choose when to send it using the `inject` method call.

```java
package com.example.test;

import dev.ceymikey.net.DiscordPayload;
import dev.ceymikey.payloads.Embed;

public class TestClass {
    public void testMethod() {
        /* Using the library you can build different payloads */
        /* An example is the embed payload */
        Embed builder = new Embed.Construct()
                .setUrl("EMBED_URL")
                .setAuthor("Small text at the top of the embed")
                .setTitle("This is a test embed title!")
                .addField("from", "field", true)
                .setDescription("This is a test embed description!")
                .setColor(12370112)
                .setFooter("Small text at the bottom of the embed")
                .build();

        /* This sends the actual payload */
        /* This gives you the freedom to build the payload early */
        /* and send it later on in your code whenever you want */
        DiscordPayload.inject(builder);
    }
}
```
### Result
This will be the result of our code after calling the `testMethod` with 
the correct webhook url.
<img align="left" height="148" src="https://i.ibb.co/HL2jZchm/image.png">

### Thanks to contributors
<a href="https://github.com/Ceymikey/discord-toolchain/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=ceymikey/discord-toolchain" />
</a>