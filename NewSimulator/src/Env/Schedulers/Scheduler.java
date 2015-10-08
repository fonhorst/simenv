package Env.Schedulers;

import Env.Context;
import Env.Entities.Schedule;
import Env.Entities.Task;

import java.util.ArrayList;

/**
 * Created by Mishanya on 08.10.2015.
 */
public interface Scheduler {

    Schedule schedule(Context ctx, ArrayList<Task> tasks);
    ArrayList<Task> reschedule(Context ctx);
}
