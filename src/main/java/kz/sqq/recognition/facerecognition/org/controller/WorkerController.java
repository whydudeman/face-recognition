package kz.sqq.recognition.facerecognition.org.controller;

import kz.sqq.recognition.facerecognition.org.form.SearchForm;
import kz.sqq.recognition.facerecognition.org.form.WorkerForm;
import kz.sqq.recognition.facerecognition.org.model.Schedule;
import kz.sqq.recognition.facerecognition.org.model.Worker;
import kz.sqq.recognition.facerecognition.org.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @GetMapping()
    public String getAllWorkers(@RequestParam(name = "field",defaultValue = "") String field,Model model) {
        System.out.println(field);
        return getAll(model, workerService.getAllBySearchText(field));
    }
    @GetMapping(value = "all")
    public String getAll(Model model, List<Worker> workers) {
        model.addAttribute("workers", workers);
        model.addAttribute("searchText", new SearchForm());
        return "worker/all";
    }
    @PostMapping("upload/{id}")
    public String uploadFile(@PathVariable Long id, @RequestParam("files") MultipartFile[] files, RedirectAttributes attributes) {
        workerService.uploadImageGetPath(files,id);
        return "redirect:/worker";
    }

    @GetMapping("{id}")
    public String getById(@PathVariable Long id, Model model,@RequestParam(name = "period",defaultValue = "10") Integer period) {
        Worker worker = workerService.getById(id);
        model.addAttribute("worker", worker);
        List<Schedule> arrives = workerService.getArrivesOfWorkerByPeriod(worker,period);
        model.addAttribute("arrives", arrives);
        model.addAttribute("workerForm", new WorkerForm());
        return "worker/one";
    }

    @PostMapping("create")
    public String createProductWithPrice(@ModelAttribute WorkerForm workerForm) {
        workerService.createWorker(workerForm);
        return "redirect:/worker";
    }

    @PostMapping("edit/{id}")
    public String editWorker(@PathVariable Long id, @ModelAttribute WorkerForm workerForm) {
        workerService.updateFromForm(id,workerForm);
        return "redirect:/worker/"+id;
    }

    @GetMapping("edit/{id}")
    public String editWorkerPage(@PathVariable Long id,Model model) {

        model.addAttribute("worker",workerService.getById(id));

        return "redirect:/worker/"+id;
    }

    @GetMapping("create")
    public String createPage(Model model) {
        model.addAttribute("workerForm", new WorkerForm());
        return "worker/create";
    }
}
