package com.datastax.demo.stargate.chevrons;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chevrons")
@CrossOrigin(
        methods = {PUT, POST, GET, OPTIONS, DELETE, PATCH},
        maxAge = 3600,
        allowedHeaders = {"x-requested-with", "origin", "content-type", "accept"},
        origins = "*"
 )
public class ChevronRestController {
    
    private ChevronRepository repo;
    
    public ChevronRestController(ChevronRepository chevronRepo) {
        this.repo = chevronRepo;
    }
    
    @GetMapping
    public List<Chevron> findAll(HttpServletRequest req) {
        return repo.findAll();
    }
 
    @GetMapping("/{area}")
    public List<Chevron> findByArea(HttpServletRequest req, @PathVariable(value = "area") String area) {
        return repo.findByKeyArea(area);
    }
    
    @GetMapping("/{area}/{code}")
    public ResponseEntity<Chevron> findAChevron(HttpServletRequest req, 
            @PathVariable(value = "area") String area,
            @PathVariable(value = "code") int code) {
        Optional<Chevron> chevron = repo.findById(new ChevronPrimaryKey(area, code));
        if (chevron.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chevron.get());
    }

}
