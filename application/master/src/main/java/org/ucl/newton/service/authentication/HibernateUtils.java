package org.ucl.newton.service.authentication;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Instances of this class provide utility functions for use with the
 * hibernate library.
 *
 * @author Blair Butterworth
 */
public class HibernateUtils
{
    public static <T> T getSingleResultOrNull(Query<T> query){
        List<T> results = query.getResultList();
        if (results.isEmpty()) return null;
        else if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException(results.size());
    }
}
