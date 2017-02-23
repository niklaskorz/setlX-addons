# SetlX Addons

This repository contains my addons to the SetlX interpreter.

## HTTP (work in progress)

The `setlX-http` addon defines functions for making HTTP requests
in SetlX. Under the hood, it uses
[OkHttp](http://square.github.io/okhttp/)
and
[JSON-java](https://github.com/stleary/JSON-java).
You can find a prebuilt version of the addon under
the [release tab](https://github.com/niklaskorz/setlX-addons/releases)
of this repository.

### Dependencies

- [Gradle](https://gradle.org/)
- Java 1.7+

### Build instructions

Before building, make sure to install the `org.randoom.setlx:setlX-core:2.6.0`
package to your local maven repository. This can be done by running
`mvn clean install` in the `interpreter` directory of the
[SetlX repository](https://github.com/herrmanntom/setlX/tree/v2.6.0).
In the code snippet below, replace the string `$setlXJarDirectory` with the value that
is asigned to the variable setlXJarDirectory in your SetlX start script.  In order
to install the addons, open a shell in the directory where setlx-addons.  Then,
building the addons is achieved with the following commands:

- `cd http`
- `gradle clean fatJar`
- `cp build/libs/setlX-http.jar $setlXJarDirectory`

This will make the functions `http_get` and `http_getJson` available in your SetlX interpreter.

### Usage example

```stlx
url := "https://api.ipify.org/";
ip := http_get(url);
print("My IP address is ", ip);

url := "https://api.spotify.com/v1/search?type=track&query=Afraid%20of%20heights";
data := http_getJson(url);
track := data["tracks"]["items"][1];
artist := track["artists"][1];
trackName := track["name"];
artistName := artist["name"];
trackDuration := track["duration_ms"];
trackPreview := track["preview_url"];
print("The song $trackName$ by $artistName$ has a duration of $trackDuration$ milliseconds.");
print("You can listen to a preview of it on $trackPreview$");
```