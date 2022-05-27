package com.boraozdogan.kekikpanel.controller.api;

import com.boraozdogan.kekikpanel.model.Task;
import com.boraozdogan.kekikpanel.model.TaskRequestModel;
import com.boraozdogan.kekikpanel.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class TasksRoute {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/api/tasks")
    public List<Task> all() {
        var result = new ArrayList<Task>();
        for(var it: taskRepository.findAll()) {
            result.add(it);
        }

        return result;
    }

    @PostMapping("/api/tasks")
    public Task newTask(@RequestBody TaskRequestModel taskRequest) {
        var task = new Task(
                taskRequest.owner(),
                taskRequest.body(),
                LocalDate.now());

        taskRepository.save(task);
        return task;
    }

    @PutMapping("/api/tasks/{id}")
    public Task editTask(
            @PathVariable int id,
            @RequestBody Map<String, Object> taskMap
    ) {
        var taskOpt = taskRepository.findById(id);
        if(taskOpt.isPresent()) {
            var task = taskOpt.get();
            task.setBody(taskMap.get("body").toString());

            return taskRepository.save(task);
        } else {
            throw new IllegalStateException("Task not found.");
        }
    }

    @DeleteMapping("/api/tasks/{id}")
    public void deleteUser(@PathVariable int id) {
        taskRepository.deleteById(id);
    }
}
