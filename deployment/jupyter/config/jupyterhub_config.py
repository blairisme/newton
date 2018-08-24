#
# Newton (c)
#
# This work is licensed under the MIT License. To view a copy of this
# license, visit
#
#      https://opensource.org/licenses/MIT
#
# Author: Blair Butterworth
#

import os
import sys
import inspect
import platform
from os import path

current_path = path.realpath(path.abspath(path.split(inspect.getfile(inspect.currentframe()))[0]))
if current_path not in sys.path:
    sys.path.insert(0, current_path)

from newtonspawner import NewtonSpawner
from newtonauthenticator import NewtonAuthenticator

#------------------------------------------------------------------------------
# Jupyter Hub Configuration
#------------------------------------------------------------------------------

#c.JupyterHub.ssl_key = '/etc/jupyterhub/privkey.pem'
#c.JupyterHub.ssl_cert = '/etc/jupyterhub/fullchain.pem'


#------------------------------------------------------------------------------
# Newton Spawner configuration
#------------------------------------------------------------------------------

def create_dir_hook(spawner):
    experiment_id = spawner.authenticator.experiment_id
    experiment_path = '/home/newton/experiment/' + experiment_id + '/repository/data/'
    data_source_ids = spawner.authenticator.data_sources
    data_source_path = '/home/newton/data/'

    if not os.path.exists(experiment_path):
        os.mkdir(experiment_path, 0o755)

    for data_source_id in data_source_ids:
        source_path = data_source_path + data_source_id
        destination_path = experiment_path + data_source_id

        if path.exists(source_path) and not path.exists(destination_path):
            os.symlink(source_path, destination_path)


c.JupyterHub.spawner_class = NewtonSpawner
c.Spawner.default_url = '/lab'
c.Spawner.ip = '0.0.0.0'
c.Spawner.args = ['--allow-root']
c.Spawner.notebook_dir = '/home/newton/experiment/{experiment_id}/repository'
c.Spawner.disable_user_config = True
c.Spawner.pre_spawn_hook = create_dir_hook


#------------------------------------------------------------------------------
# Newton Authenticator configuration
#------------------------------------------------------------------------------

c.JupyterHub.authenticator_class = NewtonAuthenticator

# The certificate used to sign the incoming JSONWebToken, must be in PEM Format
c.NewtonAuthenticator.signing_certificate = path.join(current_path, "newton.key")

# The claim field contianing the username
c.NewtonAuthenticator.username_claim_field = 'sub'

# This config option should match the aud field of the JSONWebToken, empty string to disable the validation of this field.
c.NewtonAuthenticator.expected_audience = 'www.newton.com'

# The URL to use after succesful authentication
c.NewtonAuthenticator.forward_url = 'spawn'


#------------------------------------------------------------------------------
# Local Authenticator configuration
#------------------------------------------------------------------------------

c.LocalAuthenticator.create_system_users = True

if platform.system() == 'Linux':
    c.LocalAuthenticator.add_user_cmd = ['adduser', '-g', 'root', '--create-home']

if platform.system() == 'Darwin':
    script_path = path.join(current_path, "adduser")
    c.LocalAuthenticator.add_user_cmd = [script_path, '-p', 'password', '-a', '-u']
