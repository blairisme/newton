import os
import sys
import inspect
import platform
from os import path

current_path = path.realpath(path.abspath(path.split(inspect.getfile(inspect.currentframe()))[0]))
auth_path = path.join(current_path, "jwtauthenticator")
if auth_path not in sys.path:
    sys.path.insert(0, auth_path)

from jwtauthenticator import JSONWebTokenLocalAuthenticator


#------------------------------------------------------------------------------
# Spawner configuration
#------------------------------------------------------------------------------

c.Spawner.default_url = '/lab'

if platform.system() == 'Linux':
    c.LocalAuthenticator.add_user_cmd = ['adduser']

if platform.system() == 'Darwin':
    script_path = path.join(current_path, "adduser")
    c.LocalAuthenticator.add_user_cmd = [script_path, '-p', 'password', '-u']


#------------------------------------------------------------------------------
# Authenticator configuration
#------------------------------------------------------------------------------

c.JupyterHub.authenticator_class = JSONWebTokenLocalAuthenticator

# The certificate used to sign the incoming JSONWebToken, must be in PEM Format
c.JSONWebTokenAuthenticator.signing_certificate = '/Users/blair/Code/newton/deployment/jupyter/newton.key'

# The claim field contianing the username
c.JSONWebTokenAuthenticator.username_claim_field = 'sub'

# This config option should match the aud field of the JSONWebToken, empty string to disable the validation of this field.
c.JSONWebTokenAuthenticator.audience = 'www.newton.com'

# The URL to use after succesful authentication
c.JSONWebTokenAuthenticator.forward_url = 'spawn'

# Enables local user creation upon authentication, requires JSONWebTokenLocalAuthenticator
c.JSONWebLocalTokenAuthenticator.create_system_users = True

c.LocalAuthenticator.create_system_users = True
