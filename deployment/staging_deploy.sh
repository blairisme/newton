#!/usr/bin/env sh

# Newton (c)
#
# This work is licensed under the MIT License. To view a copy of this
# license, visit
#
#      https://opensource.org/licenses/MIT
#
# A convenience script to deploy newton to staging servers.
#
# Author: Blair Butterworth
#

if ! [ -x "$(command -v brew)" ]; then
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
fi

if ! [ -x "$(command -v ansible)" ]; then
    brew install ansible
    brew install http://git.io/sshpass.rb
fi

ansible-playbook -i staging_hosts.yml deploy_master.yml
