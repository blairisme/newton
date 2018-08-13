package org.ucl.newton.fizzyo.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Instances of this class provide Fizzyo data to the Newton system.
 *
 * @author Xiaolong Chen
 */
public class GameSession extends FizzyoDataUnit{
    private String id;
    private String gameId;
    private String userId;
    private String startTime;
    private String endTime;
    private int score;
    private int breathCount;
    private int setCount;
    private int goodBreathCount;
    private int badBreathCount;

    @Override
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("id");
        keys.add("gameId");
        keys.add("userId");
        keys.add("startTime");
        keys.add("endTime");
        keys.add("score");
        keys.add("breathCount");
        keys.add("setCount");
        keys.add("goodBreathCount");
        keys.add("badBreathCount");
        return keys;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        values.add(id);
        values.add(gameId);
        values.add(userId);
        values.add(startTime);
        values.add(endTime);
        values.add(Integer.toString(score));
        values.add(Integer.toString(breathCount));
        values.add(Integer.toString(setCount));
        values.add(Integer.toString(goodBreathCount));
        values.add(Integer.toString(badBreathCount));
        return values;

    }


}
