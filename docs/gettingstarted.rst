***************
Getting Started
***************

To get started first clone the Newton repository.

.. code:: bash

    git clone https://github.com/blairisme/newton.git

Once the repository has been cloned the system can be started either via
:ref:`docker <via-docker>` or via the :ref:`command line <via-command>`.
Docker does not require the installation of additional software of frameworks
but does take time to set up and requires more disk space.

.. _via-docker:

Via Docker
====================

Ensure you have `Docker <https://www.docker.com/>`_ and `Docker-Compose <https://docs.docker.com/compose/>`_
installed and then run the following command.

.. code:: bash

    gradlew systemStart

This will instruct Docker to download and install all the dependencies and
software required by Newton. Once complete the Newton web app, data processing
slave, integrated Jupyter Hub and database servers will be started. Once started
the Newton user interface can be found :ref:`here <via-location>`.

.. _via-command:

Via the Command Line
====================

The Newton system can also be run locally. To do so issue the following commands
to start the various system components.

To start the Newton web app with an ``in-memory database`` run the following.

.. code:: bash

    gradlew appStart

To start the Newton data processing slave run the following in another terminal.

.. code:: bash

    gradlew slaveStart

These commands will start the main Newton application and an accompanying data
processing slave. These commands ``wont start the Jupyter Hub integration``.
Once started the Newton user interface can be found :ref:`here <via-location>`.

.. _via-location:

Locations
====================

Once started the Newton user interface can be found here.

.. code-block:: none

    http://localhost:9090
    https://localhost:8443

.. note::

  Browsing to https://localhost:8443 may show an insecure warning. This is
  because the certificate Newton uses is only valid for http://blairbutterworth.com,
  not localhost. It is safe to accept the security warning and proceed.

Depending on the in which the Newton system was started the following services
might also be available.

.. code-block:: none

    [Database] http://localhost:3306
    [Processor] http://localhost:8080
    [Jupyter Hub] http://localhost:8000
