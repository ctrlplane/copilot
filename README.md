# copilot
Key broker service
## Status
![copilot CI](https://github.com/ctrlplane/copilot/actions/workflows/copilot-build.yaml/badge.svg)
[![CodeQL](https://github.com/ctrlplane/copilot/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/ctrlplane/copilot/actions/workflows/codeql-analysis.yml)

## Running
To run with podman:

```podman run -d -p 8080:8080 quay.io/ctrlplane/copilot:0.0.1-SNAPSHOT```

This exposes port 8080 to `pilot` on localhost.

## Building from source

From the top level of the repository:

```$ mvn clean package```

A successful build should output something like:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.979 s
[INFO] Finished at: 2022-05-02T20:33:57-04:00
[INFO] ------------------------------------------------------------------------
```

This will create an executable `.jar` file in the `target` directory.

## License
Copyright © 2022, Control Plane Software, LLC. Released under the [GPL-3.0 License.](https://github.com/ctrlplane/pilot/blob/main/LICENSE)
