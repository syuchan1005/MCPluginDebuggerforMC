# What is this
It is for easy debugging Bukkit/Spigot Plugin.

# How to use
1. Download Latest Release (https://github.com/syuchan1005/MCPluginDebuggerforMC/releases) and put you server.
2. Run server and open plugins/MCPluginDebugger/config.yml

```yaml:config.yml
debug:
  pluginName: Debug_Target_Plugin_Name
  socketPort: 9000
```

3. Do any of the below

<details>
<summary>Maven</summary>
<p>

1. open your pom.xml, and write this. (in <repositories> tag)

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

2. write this.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.syuchan1005</groupId>
            <artifactId>MCPluginDebuggerforMaven-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>send</goal>
                    </goals>
                    <configuration>
                        <host>write your setting!</host>
                        <port>write your setting!</port>
                        <pluginName>write your setting!</pluginName>
                        <jarFilePath>${basedir}/write/your/jar/path</jarFilePath>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

3. `mvn package`

</p>
</details>

<details>
    <summary>IntelliJ IDEA</summary>
    <p>

1. Open your IntelliJ IDEA. and open plugins setting.
2. Click `Browse repositories...`.
3. type `MCPluginDebugger` in SearchBox.
4. click `Install` and restart it!
5. Open `MCDebugConsole` in IntelliJ IDEA and write your config. 
7. Click `Start` in `MCDebugConsole`
8. Edit Source and Compile
9. Click `ReloadPlugin`

    </p>
</details>

## Sorry, UnImplements.↓
<details>
    <summary>Eclipse</summary>
    <p>
    Sorry...
    </p>
</details>

<details>
    <summary>Netbeans</summary>
    <p>
    Sorry...
    </p>
</details>