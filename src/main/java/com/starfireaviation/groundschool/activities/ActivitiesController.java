package com.starfireaviation.groundschool.activities;

import com.starfireaviation.groundschool.common.ActivityType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/activities")
class ActivitiesController {

    private final Activities activities;

    ActivitiesController(Activities activities) {
        this.activities = activities;
    }

    @PostMapping
    void create(@RequestBody Activity activity) {
        this.activities.create(activity);
    }

    @GetMapping("/{activityId}")
    Activity get(@PathVariable("activityId") Long activityId) {
        return this.activities.get(activityId);
    }

    @PutMapping("/{activityId}")
    void update(@PathVariable("activityId") Long activityId, @RequestBody Activity activity) {
        this.activities.update(activityId, activity);
    }

    @DeleteMapping("/{activityId}")
    void delete(@PathVariable("activityId") Long activityId) {
        this.activities.delete(activityId);
    }

    @GetMapping
    List<Activity> find() {
        return this.activities.find();
    }
}

@Service
@Transactional
class Activities {
    private final ActivityRepository activityRepository;

    Activities(ActivityRepository repository) {
        this.activityRepository = repository;
    }

    void create(Activity activity) {
        var saved = this.activityRepository.save(activity);
        System.out.println("saved [" + saved + "]");
    }

    public Activity get(Long activityId) {
        return null;
    }

    public void update(Long activityId, Activity activity) {
    }

    public void delete(Long activityId) {
    }

    public List<Activity> find() {
        return new ArrayList<>();
    }
}

interface ActivityRepository extends ListCrudRepository<Activity, Long> {
}

@Table("activities")
record Activity(@Id Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String duration,
                ActivityType activityType, Long referenceId) {
}