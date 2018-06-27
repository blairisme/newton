/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Instances of this class represent a research project, a container for
 * experiments and associated data sources.
 *
 * @author Blair Butterworth
 */
public class Project
{
    private String id;
    private String name;
    private Date updated;
    private ArrayList<String> members;
    private int stars;

    public Project(String id, String name, Date updated, int stars) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.stars = stars;
        this.members = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLastUpdated() {
        return updated;
    }

    public String getLastUpdatedDescription() {
        PrettyTime timeFormatter = new PrettyTime();
        return timeFormatter.format(updated);
    }
    public ArrayList<String> getMembers(){ return members; }
    public void addMember(String contributor){
        members.add(contributor);
    }
    public int getStars() {
        return stars;
    }
}
