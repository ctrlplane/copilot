package io.ctrlplane.copilot.request;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.vault.core.ReactiveVaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.key.IKeyServer;
import io.ctrlplane.copilot.model.VaultKeyResponse;

import reactor.core.publisher.Mono;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
/** Tests Request controller. */
class RequestControllerTest {

    /** Mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** Mock for vault bean. */
    @MockBean
    private IKeyServer mockReactiveVaultTemplate;

    /** Mock for mongo repository. */
    @MockBean
    private RequestRepository mockRequestRepository;

    /** Mock for response from vault. */
    @Mock
    private VaultResponse mockVaultResponse;

    /** Mock for key response. */
    @Mock
    private VaultKeyResponse mockVaultKeyResponse;

    /** Mock for response support. */
    @Mock
    private VaultResponseSupport<VaultKeyResponse> mockVaultResponseSupport;

    /** Tests {@link RequestController#getKey(String)} */
    @Test
    void testGetKek() throws Exception {
        final String test = "dGVzdA==";
        when(mockVaultKeyResponse.getData()).thenReturn(Map.of("key", "val"));

        when(mockVaultResponseSupport.getRequiredData()).thenReturn(mockVaultKeyResponse);

        Mockito.when(this.mockReactiveVaultTemplate.read("test")).thenReturn(mockVaultResponseSupport);
        ResultActions result = this.mockMvc.perform(get("/api/v1/key/" + test)).andDo(print())
                .andExpect(status().isOk());

        Map<String, String> resultMap = SerializationUtils
                .deserialize(result.andReturn().getResponse().getContentAsByteArray());

        assertEquals("val", resultMap.get("key"));
    }

    /** Tests {@link RequestController#getKey(String)} */
    @Test
    void testGetKekNotFound() throws Exception {
        final String test = "dGVzdA==";
        when(mockVaultKeyResponse.getData()).thenReturn(Map.of("key", "val"));

        when(mockVaultResponseSupport.getRequiredData()).thenReturn(mockVaultKeyResponse);

        Mockito.when(this.mockReactiveVaultTemplate.read("test")).thenReturn(null);
        this.mockMvc.perform(get("/api/v1/key/" + test)).andDo(print())
                .andExpect(status().isNotFound());
    }
}
