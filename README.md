**[Getting Started](#getting-started)** |
**[Documentation](https://newton.readthedocs.io/)** |
**[License](#licence)** |

# Newton

[![Build Status](https://travis-ci.com/blairisme/newton.svg?branch=master)](https://travis-ci.com/blairisme/newton)
[![Documentation Status](https://readthedocs.org/projects/newton/badge/?version=latest)](https://newton.readthedocs.io/en/latest/?badge=latest)
[![Code Coverage](https://codecov.io/gh/blairisme/newton/branch/master/graph/badge.svg)](https://codecov.io/gh/blairisme/newton)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6d4ca6c355fc4a82aa773e7b5cf96585)](https://www.codacy.com/app/blairisme/newton?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=blairisme/newton&amp;utm_campaign=Badge_Grade)
[![GitHub release](https://img.shields.io/badge/release-v0.1-blue.svg)](https://github.com/blairisme/newton/releases/tag/v0.1)
[![License: AGPL-3.0](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/blairisme/newton/blob/master/LICENSE

Newton is a data analysis platform created by a team from the Computer Science
department of University College London. It provides users the ability to carry
out data science operations on a variable set of data, automatically fetched and
made available for analysis.

Analysis tasks are contained in projects, a logical grouping of data ingested in
into the system and the analytical processes that operate on this data, called
experiments. Experiments allow a group of users to collaboratively design an
analytical process that transforms ingested data into a meaningful outcome.

One way we facilitate this collaboration is via the use of Jupyter notebooks, a
widely used tool in the data scientist community. The Newton system provides an
integration with Jupyter Hub and Jupyter Lab, facilitating the creation and
editing of Jupyter notebooks.


Read the latest version of our documentation on
[ReadTheDocs](https://newton.readthedocs.io/en/latest/).

---
<a name="getting-started"></a>

## Getting started

To get started first clone the Newton repository.

```
    git clone https://github.com/blairisme/newton.git
```

Once the repository has been cloned the system can be started either via
[Docker](#via-docker) or via the [command line](#via-command). Docker does not
require the installation of additional software or frameworks but does take time
to set up and requires more disk space.

<a name="via-docker"></a>

### Via Docker

Ensure you have [Docker](https://www.docker.com/) and
[Docker-Compose](https://docs.docker.com/compose/) installed and then run the
following command.

```
    gradlew systemStart
```

This will instruct Docker to download and install all the dependencies and
software required by Newton. Once complete the Newton web app, data processing
slave, integrated Jupyter Hub and database servers will be started. Once started
the Newton user interface can be found [here](#via-location).

<a name="via-command"></a>

### Via the Command Line

The Newton system can also be run locally. To do so issue the following commands
to start the various system components.

To start the Newton web app with an ``in-memory database`` run the following.

```
    gradlew appStart
```

To start the Newton data processing slave run the following in another terminal.

```
    gradlew slaveStart
```

These commands will start the main Newton application and an accompanying data
processing slave. These commands ``wont start the Jupyter Hub integration``.
Once started the Newton user interface can be found [here](#via-location).

<a name="via-location"></a>

### Locations

Once started the Newton user interface can be found here.

```
    http://localhost:9090
    https://localhost:8443
```

Depending on the in which the Newton system was started the following services
might also be available.

```
    [Database] http://localhost:3306
    [Processor] http://localhost:8080
    [Jupyter Hub] http://localhost:8000
```

### Note

Browsing to https://localhost:8443 may show an insecure warning. This is because
the certificate Newton uses is only valid for https://blairbutterworth.com, not
localhost. It is safe to accept the security warning and proceed.

<a name="licence"></a>

## Licence

Newton is licensed under the terms of the
[AGPL-3.0 license](https://github.com/blairisme/newton/blob/master/LICENSE).
