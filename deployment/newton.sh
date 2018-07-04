#!/usr/bin/env sh

# Newton (c)
#
# This work is licensed under the MIT License. To view a copy of this
# license, visit
#
#      https://opensource.org/licenses/MIT
#
# This script starts the Newton web server application using the production
# profile.
#
# Author: Blair Butterworth
#

cd /root/code/newton
./gradlew appStart -Prun.profile=production
