package ua.com.foxminded.krailo.university.restControllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.service.AudienceService;

@RestController
@RequestMapping("rest/audiences")
public class AudienceRestController {

    private AudienceService audienceService;

    public AudienceRestController(AudienceService audienceService) {
	this.audienceService = audienceService;
    }

    @GetMapping
    public Page<Audience> getAllAudiences(Pageable pageable) {
	return audienceService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Audience getAudience(@PathVariable int id) {
	return audienceService.getById(id);
    }

    @PostMapping()
    public void saveAudeince(@RequestBody Audience audience) {
	audienceService.create(audience);
    }

    @PutMapping()
    public void editAudience(@RequestBody Audience audience) {
	audienceService.create(audience);
    }
    
    @DeleteMapping("/{id}")
    public void deleteAudience(@PathVariable int id) {
	audienceService.delete(audienceService.getById(id));
    }

}
