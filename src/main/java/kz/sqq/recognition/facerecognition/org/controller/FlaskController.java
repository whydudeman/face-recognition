package kz.sqq.recognition.facerecognition.org.controller;

import kz.sqq.recognition.facerecognition.org.form.ScheduleDto;
import kz.sqq.recognition.facerecognition.org.service.FlaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("face-recognition/flask")
public class FlaskController {
    @Autowired
    private FlaskService flaskService;
    @PostMapping(path = "schedule")
    public ResponseEntity updateSchedule(@RequestBody ScheduleDto scheduleDto){
        return flaskService.getArriveFromFlask(scheduleDto);
    }
}
