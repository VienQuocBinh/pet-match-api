package petmatch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import petmatch.model.User;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {
    @GetMapping("/v1/hello-world")
    public ResponseEntity<User> helloWorld() {
        log.info("Returning hello world");
//        return new ResponseEntity<>(User.builder().greeting("Hello World").build(), HttpStatus.CREATED);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
