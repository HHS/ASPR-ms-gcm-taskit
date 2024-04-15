[![GPL LICENSE][license-shield]][license-url]
[![GitHub tag (with filter)][tag-shield]][tag-url]
[![GitHub contributors][contributors-shield]][contributors-url]
[![GitHub Workflow Status (with event)][dev-build-shield]][dev-build-url]
[![GitHub Workflow Status (with event)][build-shield]][build-url]

# GCM Taskit
An implementation of [Taskit](https://github.com/HHS/ASPR-ms-taskit) for use with [GCM](https://github.com/HHS/ASPR-8).

Currently there is only 1 supported serialzation format, and that is protobuf. Other formats such as binary will follow in the future.

## License
Distributed under the GPLv3 License. See [LICENSE](LICENSE) for more information.

Please read the [HHS vulnerability discloure](https://www.hhs.gov/vulnerability-disclosure-policy/index.html).

## Overview
This taskit implementation is built as follows:

### Plugins
Each GCM plugin has it's PluginData and most support and testsupport classes are implemented and support serilization. 

Note that Plugins themselves do not support serialization, but their respective PluginDatas do.

These are split into Translators and use a dependency mechanism that is the same as the plugin dependency within GCM.

### Nucleus
Not all classes in Nucleus support serialization.
Just the following classes support serialization
- Planner
- SimulationState
- ExperimentParameterData
- Dimension

## Building from Source

### Requirements
- Maven 3.8.x
- Java 17
- Favorite IDE for Java development
- Modeling Util located [here](https://github.com/HHS/ASPR-ms-util)
- GCM located [here](https://github.com/HHS/ASPR8)
- Taskit located [here](https://github.com/HHS/ASPR-ms-taskit)

*NOTE that both Taskit and Modeling Util are in maven central, so there is no need to clone and build those repos

### Building
To build this project:
- Clone the repo
- open a command line terminal
- navigate to the root folder of this project
- run the command: `mvn clean install`

## Documentation
Documentation has yet to be created. In the interim, the code is mostly commented and the javadocs do provide good detail with regards to method and class expectations. 

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/HHS/ASPR-ms-gcm-taskit
[contributors-url]: https://github.com/HHS/ASPR-ms-gcm-taskit/graphs/contributors
[tag-shield]: https://img.shields.io/github/v/tag/HHS/ASPR-ms-gcm-taskit
[tag-url]: https://github.com/HHS/ASPR-ms-gcm-taskit/releases/latest
[license-shield]: https://img.shields.io/github/license/HHS/ASPR-ms-gcm-taskit
[license-url]: LICENSE
[dev-build-shield]: https://img.shields.io/github/actions/workflow/status/HHS/ASPR-ms-gcm-taskit/dev_build.yml?label=dev-build
[dev-build-url]: https://github.com/HHS/ASPR-ms-gcm-taskit/actions/workflows/dev_build.yml
[build-shield]: https://img.shields.io/github/actions/workflow/status/HHS/ASPR-ms-gcm-taskit/release_build.yml?label=release-build
[build-url]: https://github.com/HHS/ASPR-ms-gcm-taskit/actions/workflows/release_build.yml.yml

