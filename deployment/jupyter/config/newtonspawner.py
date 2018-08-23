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

from jupyterhub.spawner import LocalProcessSpawner

class NewtonSpawner(LocalProcessSpawner):
    def template_namespace(self):
        result = super().template_namespace()
        result['experiment_id'] = super().authenticator.experiment_id
        return result
