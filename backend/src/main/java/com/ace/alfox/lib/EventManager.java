package com.ace.alfox.lib;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EventManager {
  private final ScheduledThreadPoolExecutor threadPoolExecutor;
  private final HashMap<Long, Set<TaskMetaDataWithFuture>> playerTasks;

  private EventManager() {
    playerTasks = new HashMap<>();
    threadPoolExecutor = new ScheduledThreadPoolExecutor(1);
  }

  public ScheduledFuture schedule(Duration delay, Runnable runnable, TaskMetaData meta) {
    var scheduledFuture =
        threadPoolExecutor.schedule(runnable, delay.toMillis(), TimeUnit.MILLISECONDS);
    if (meta != null) {
      Set<TaskMetaDataWithFuture> tasks;
      synchronized (playerTasks) {
        if (playerTasks.containsKey(meta.getPlayer())) {
          tasks = playerTasks.get(meta.getPlayer());
          tasks = cleanOutPlayerTasks(tasks);
        } else {
          tasks = new HashSet<>();
        }
        var _meta = meta;
        // I never remove tasks.
        tasks.add(
            new TaskMetaDataWithFuture() {
              {
                meta = _meta;
                future = scheduledFuture;
              }
            });
        playerTasks.put(meta.getPlayer(), tasks);
      }
    }
    return scheduledFuture;
  }

  public Set<TaskMetaDataWithFuture> getScheduledTasks(long playerId) {
    synchronized (playerTasks) {
      var tasks = playerTasks.get(playerId);
      tasks = cleanOutPlayerTasks(tasks);
      playerTasks.put(playerId, tasks);
      return tasks;
    }
  }

  public long getTimeToNextAction(long playerId) {
    Set<TaskMetaDataWithFuture> tasks;
    synchronized (playerTasks) {
      tasks = playerTasks.get(playerId);
    }
    var max = 0L;
    if (tasks == null) return max;
    for (var task : tasks) {
      if (task.meta.isBlocking()) {
        max = Math.max(max, task.future.getDelay(TimeUnit.MILLISECONDS));
      }
    }
    return max;
  }

  /**
   * Cleans out tasks that are done from the given Set.
   *
   * @param tasks the Set to be filtered
   * @return a new instance of Set with only ScheduledFutures that haven't yet been completed.
   */
  private Set<TaskMetaDataWithFuture> cleanOutPlayerTasks(Set<TaskMetaDataWithFuture> tasks) {
    return tasks.stream().filter(task -> !task.future.isDone()).collect(Collectors.toSet());
  }

  public interface TaskMetaData {
    default String getType() {
      return "Unknown";
    }

    Long getPlayer();

    default boolean isBlocking() {
      return true;
    }
  }

  public static class TaskMetaDataWithFuture {
    public TaskMetaData meta;
    public ScheduledFuture future;
  }
}
