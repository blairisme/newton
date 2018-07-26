package org.ucl.FizzyoDataProvider.Fizzyo.model;
/**
 * Instances of this class provide org.ucl.FizzyoDataProvider.Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class FizzyoToken {
    String accessToken;
    int expiresIn;
    FizzyoUser user;

    public String getAccessToken() { return accessToken; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public int getExpiresIn() { return expiresIn; }

    public void setExpiresIn(int expiresIn) { this.expiresIn = expiresIn; }

    public FizzyoUser getUser() { return user; }

    public void setUser(FizzyoUser user) { this.user = user; }
}
