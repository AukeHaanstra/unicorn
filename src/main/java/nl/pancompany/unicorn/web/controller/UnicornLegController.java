package nl.pancompany.unicorn.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegPosition;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.QueryLegDto;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.web.mapper.LegViewMapper;
import nl.pancompany.unicorn.web.model.LegView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/unicorns/{unicornId}/legs/{legPosition}")
public class UnicornLegController {

    private final LegViewMapper legViewMapper;
    private final UnicornLegService unicornLegService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<LegView> getLeg(@PathVariable("unicornId") String unicornId,
                                          @PathVariable("legPosition") LegPosition legPosition) {
        LegDto leg = unicornLegService.getLeg(new QueryLegDto(UnicornId.of(unicornId), legPosition));
        return ResponseEntity.ok(legViewMapper.map(leg));
    }

    @PatchMapping
    public ResponseEntity<LegView> patchLeg(@PathVariable("unicornId") String unicornId,
                                      @PathVariable("legPosition") LegPosition legPosition,
                                      @RequestBody JsonPatch jsonPatch)
            throws JsonPatchException, JsonProcessingException {
        LegDto legDto = unicornLegService.getLeg(new QueryLegDto(UnicornId.of(unicornId), legPosition));
        LegView legView = patch(legViewMapper.map(legDto), jsonPatch);
        LegView updatedLegView = legViewMapper.map(unicornLegService.updateLeg(legViewMapper.map(legView, unicornId)));
        return ResponseEntity.ok(updatedLegView);
    }

    private LegView patch(LegView leg, JsonPatch patch)
            throws JsonProcessingException, JsonPatchException {
        JsonNode patched = patch.apply(objectMapper.convertValue(leg, JsonNode.class));
        return objectMapper.treeToValue(patched, LegView.class);
    }
}
