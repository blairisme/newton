#!/usr/bin/env sh

# Newton (c)
#
# This work is licensed under the MIT License. To view a copy of this
# license, visit
#
#      https://opensource.org/licenses/MIT
#
# This script builds and tests the Newton system. Specifically it lanuches a
# slave server in the background prior to running integration, so that coverage
# data for integration tests can be gathered.
#
# Author: Blair Butterworth
#

./gradlew clean build --exclude-task integrationTest
./gradlew slaveStart &
./gradlew integrationTest --exclude-task slaveStartDaemon

kill -9 $(jobs -p)
killall java

./gradlew report
