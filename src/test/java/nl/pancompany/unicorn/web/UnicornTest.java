package nl.pancompany.unicorn.web;

import nl.pancompany.unicorn.application.unicorn.service.UnicornService;
import nl.pancompany.unicorn.web.controller.UnicornController;
import nl.pancompany.unicorn.web.mapper.LegViewMapperImpl;
import nl.pancompany.unicorn.web.mapper.UnicornViewMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UnicornController.class)
@Import({UnicornViewMapperImpl.class, LegViewMapperImpl.class})
class UnicornTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    UnicornService unicornService;

    @Test
    void getLegGeneratesViewOnLeg()  {

    }

}
