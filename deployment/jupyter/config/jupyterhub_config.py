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
# Spawner configuration
#------------------------------------------------------------------------------

c.JupyterHub.spawner_class = NewtonSpawner
c.Spawner.default_url = '/lab'
c.Spawner.ip = '0.0.0.0'
c.Spawner.args = ['--allow-root']
c.Spawner.notebook_dir = '/home/newton/experiment/{experiment_id}/repository'
c.Spawner.disable_user_config = True


#------------------------------------------------------------------------------
# Authenticator configuration
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
    c.LocalAuthenticator.add_user_cmd = ['adduser', '--create-home', '--disabled-password']

if platform.system() == 'Darwin':
    script_path = path.join(current_path, "adduser")
    c.LocalAuthenticator.add_user_cmd = [script_path, '-p', 'password', '-a', '-u']


