package nl.pancompany.unicorn.web;

import nl.pancompany.unicorn.application.unicorn.domain.model.Color;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegPosition;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.QueryLegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UpdateLegDto;
import nl.pancompany.unicorn.application.unicorn.service.UnicornLegService;
import nl.pancompany.unicorn.application.unicorn.service.UnicornNotFoundException;
import nl.pancompany.unicorn.web.controller.UnicornLegController;
import nl.pancompany.unicorn.web.mapper.LegViewMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static nl.pancompany.unicorn.application.unicorn.domain.model.Color.GREEN;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Color.RED;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegSize.LARGE;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegSize.SMALL;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnicornLegController.class)
@Import(LegViewMapperImpl.class)
class UnicornLegControllerTest {

    static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UnicornLegService unicornLegService;

    final UnicornId unicornId = UnicornId.of(NIL_UUID);
    final LegPosition legPosition = LegPosition.FRONT_LEFT;

    final LegDto legDto = new LegDto(legPosition, RED, SMALL);

    @Test
    void getLegGeneratesViewOnLeg() throws Exception {
        QueryLegDto expectedInputDto = new QueryLegDto(unicornId, legPosition);
        LegDto outputDto = new LegDto(legPosition, Color.CYAN, SMALL);
        when(unicornLegService.getLeg(expectedInputDto)).thenReturn(outputDto);

        mockMvc.perform(get("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "legPosition": "FRONT_LEFT",
                            "color": "CYAN",
                            "legSize": "SMALL"
                        }
                        """));
    }

    @Test
    void getLegOfUnknownUnicornGenerates404() throws Exception {
        QueryLegDto expectedInputDto = new QueryLegDto(unicornId, legPosition);
        when(unicornLegService.getLeg(expectedInputDto)).thenThrow(UnicornNotFoundException.class);

        mockMvc.perform(get("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name()))
                .andExpect(status().isNotFound());
    }

    @Test
    void sendsColorUpdateCommandOnPatch() throws Exception {
        when(unicornLegService.getLeg(new QueryLegDto(unicornId, legPosition))).thenReturn(legDto);
        when(unicornLegService.updateLeg(new UpdateLegDto(unicornId, legPosition, GREEN, SMALL))).thenReturn(new LegDto(legPosition, GREEN, SMALL));

        mockMvc.perform(patch("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [{
                                    "op":"replace",
                                    "path":"/color",
                                    "value":"GREEN"
                                }]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "legPosition": "FRONT_LEFT",
                            "color": "GREEN",
                            "legSize": "SMALL"
                        }
                        """));
        verify(unicornLegService).updateLeg(new UpdateLegDto(unicornId, legPosition, GREEN, SMALL));
    }

    @Test
    void sendsLegSizeUpdateCommandOnPatch() throws Exception {
        when(unicornLegService.getLeg(new QueryLegDto(unicornId, legPosition))).thenReturn(legDto);
        when(unicornLegService.updateLeg(new UpdateLegDto(unicornId, legPosition, RED, LARGE))).thenReturn(new LegDto(legPosition, RED, LARGE));

        mockMvc.perform(patch("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [{
                                    "op":"replace",
                                    "path":"/legSize",
                                    "value":"LARGE"
                                }]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "legPosition": "FRONT_LEFT",
                            "color": "RED",
                            "legSize": "LARGE"
                        }
                        """));
        verify(unicornLegService).updateLeg(new UpdateLegDto(unicornId, legPosition, RED, LARGE));
    }

    @Test
    void sendsColorAndLegSizeUpdateCommandOnPatch() throws Exception {
        when(unicornLegService.getLeg(new QueryLegDto(unicornId, legPosition))).thenReturn(legDto);
        when(unicornLegService.updateLeg(new UpdateLegDto(unicornId, legPosition, GREEN, LARGE))).thenReturn(new LegDto(legPosition, GREEN, LARGE));

        mockMvc.perform(patch("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [{
                                    "op":"replace",
                                    "path":"/color",
                                    "value":"GREEN"
                                },{
                                    "op":"replace",
                                    "path":"/legSize",
                                    "value":"LARGE"
                                }]
                                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "legPosition": "FRONT_LEFT",
                            "color": "GREEN",
                            "legSize": "LARGE"
                        }
                        """));
        verify(unicornLegService).updateLeg(new UpdateLegDto(unicornId, legPosition, GREEN, LARGE));
    }

    @Test
    void updateLegOfUnknownUnicornGenerates404() throws Exception {
        when(unicornLegService.getLeg(new QueryLegDto(unicornId, legPosition))).thenReturn(legDto);
        UpdateLegDto expectedInputDto = new UpdateLegDto(unicornId, legPosition, GREEN, SMALL);
        doThrow(UnicornNotFoundException.class).when(unicornLegService).updateLeg(expectedInputDto);

        mockMvc.perform(patch("/unicorns/{unicornId}/legs/{legPosition}", unicornId.toStringValue(), legPosition.name())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [{
                                    "op":"replace",
                                    "path":"/color",
                                    "value":"GREEN"
                                }]
                                """))
                .andExpect(status().isNotFound());
    }
}
