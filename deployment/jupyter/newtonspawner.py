from jupyterhub.spawner import LocalProcessSpawner

class NewtonSpawner(LocalProcessSpawner):
    def template_namespace(self):
        result = super().template_namespace()
        result['experiment_id'] = super().authenticator.experiment_id
        return result
