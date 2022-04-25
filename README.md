# copilot
Key broker service

### Building from source
```mvn clean compile jib:build```
This builds the image and deploys it to the harbor repository.
No local docker daemon is required for the build.


### Running

To run with podman:

```podman run -d -p 8080:8080 harbor.ctrlplane.net/ctrlplane/copilot:0.0.1-SNAPSHOT```

This exposes port 8080 to pilot on localhost.
