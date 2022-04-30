# copilot
Key broker service
## Status
![copilot CI](https://github.com/ctrlplane/copilot/actions/workflows/copilot-build.yaml/badge.svg)

## Running
To run with podman:

```podman run -d -p 8080:8080 quay.io/ctrlplane/copilot:0.0.1-SNAPSHOT```

This exposes port 8080 to `pilot` on localhost.

## Building from source

From the top level of the repository:

```$ mvn -B package --file pom.xml```

This will create an executable `.jar` file in the `target` directory.
