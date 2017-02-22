# SetlX Addons

This repository contains my addons to the SetlX interpreter.

## HTTP (work in progress)

The `setlX-http` addon defines functions for making HTTP requests
in SetlX. Under the hood, it uses
[OkHttp](http://square.github.io/okhttp/)
and
[JSON-java](https://github.com/stleary/JSON-java).

### Dependencies

- [Gradle](https://gradle.org/)
- Java 1.7+

### Build instructions

Replace `$setlXJarDirectory` with the value used for the equally
called variable in your SetlX start script.

- `cd http`
- `gradle clean fatJar`
- `cp build/libs/setlX-http.jar $setlXJarDirectory`

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