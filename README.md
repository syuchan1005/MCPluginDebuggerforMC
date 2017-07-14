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
<summary>Maven(only ReloadPlugin)</summary>
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

## IDE Plugins (Launch Server, Reload Plugin and more)

<details>
    <summary>IntelliJ IDEA</summary>
    <p>
1. Open your IntelliJ IDEA. and open plugins setting.
2. Click `Browse repositories...`.
3. type `MCPluginDebugger` in SearchBox.
4. click `Install` and restart it!
5. Open `View -> Tool Windows -> MCDebugConsole` and write your config. 
    </p>
</details>

<details>
    <summary>Eclipse</summary>
    <p>
1. Open your Eclipse. and open `Help -> Install New Software...`
2. type `https://syuchan1005.github.io/MCPluginDebuggerforEclipse` in `Work with: `Box.
3. open `Debug` and check `MCPluginDebuggerforEclipse`.
4. click `Next >` two times, check `I accept....`.
5. click `Finish` and restart it!.
6. Open `Window -> Show View -> Other...`, `Debug -> MCDebugConsole` and write your config. 
    </p>
</details>

<details>
    <summary>Netbeans</summary>
    <p>
    Sorry... wait a minute.
    </p>
</details>

7. Click `Start` in `MCDebugConsole`
8. Edit Source and Compile
9. Click `ReloadPlugin`