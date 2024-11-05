package nl.pancompany.unicorn.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;
import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.web.mapper.UnicornViewMapper;
import nl.pancompany.unicorn.web.model.UnicornView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/unicorns/{unicornId}")
public class UnicornController {

    private final UnicornService unicornService;
    private final UnicornViewMapper unicornViewMapper;

    @GetMapping
    public ResponseEntity<UnicornView> getUnicorn(@PathVariable("unicornId") String unicornId) {
        UnicornDto unicorn = unicornService.getUnicorn(Unicorn.UnicornId.of(unicornId));
        return ResponseEntity.ok(unicornViewMapper.map(unicorn));
    }

}
