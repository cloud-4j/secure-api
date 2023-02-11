package com.secure.api.token.auth.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pandas")
public class PandaController {

    @GetMapping
    public ResponseEntity<List<Panda>> getPandas(){
        return ResponseEntity.ok(
                new ArrayList<>(Arrays.asList(
                        new Panda("kongfu", 12),
                        new Panda("ashpazbashi", 10))
        ));
    }

}
