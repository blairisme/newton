package security;

import helpers.Constants;

import java.io.FilePermission;
import java.security.*;

public class SandboxSecurityPolicy extends Policy {

    private String mPath;

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {
        if (isPlugin(domain)) {
            return pluginPermissions();
        }
        else {
            return applicationPermissions();
        }
    }


    public void setDirectory(String path){
        System.out.println("******* path="+path);
        mPath = path;
    }

    private boolean isPlugin(ProtectionDomain domain) {
        return domain.getClassLoader() instanceof PluginClassLoader;
    }

    private PermissionCollection pluginPermissions() {
        System.out.println("^^^^^^ plugin Permissions mPath="+mPath);
        Permissions permissions = new Permissions(); // No permissions
        permissions.add(new FilePermission(mPath+"/-", "read,write,execute"));
        permissions.add(new FilePermission(mPath, "read,write,execute"));
        
        permissions.add(new FilePermission(Constants.PYTHON_PATH, "execute"));
        return permissions;
    }

    private PermissionCollection applicationPermissions() {
        Permissions permissions = new Permissions();
        permissions.add(new AllPermission());
        return permissions;
    }
}