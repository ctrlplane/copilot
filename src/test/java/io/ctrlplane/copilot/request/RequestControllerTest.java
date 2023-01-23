package io.ctrlplane.copilot.request;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.vault.support.VaultResponse;
import org.springframework.vault.support.VaultResponseSupport;

import io.ctrlplane.copilot.key.VaultServer;
import io.ctrlplane.copilot.model.VaultKeyResponse;

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
        private VaultServer mockVaultServer;

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
        void testGetKekVault() throws Exception {
                final String test = "ewogICJrZWtfaWQiOiAiYXNkZjEyMzQiLAogICJrbXNfcHJvdmlkZXIiOiAidmF1bHQiLAogICJrbXNfcHJvdmlkZXJfcGF0aCI6ICJjdHJscGxhbmUvYXNpYS1wYWNpZmljL2FwYWMtdG9reW8iLAogICJrbXNfYWRkaXRpb25hbF9wYXJhbXMiOiB7CiAgICAgICJzZWNyZXRfaWQiOiAidGVzdEtleTEiCiAgfQp9Cg==";
                when(mockVaultKeyResponse.getData()).thenReturn(Map.of("key", "val"));
                when(mockVaultResponseSupport.getRequiredData()).thenReturn(mockVaultKeyResponse);
                when(this.mockVaultServer.getKmsResponse("ctrlplane/asia-pacific/apac-tokyo"))
                                .thenReturn(mockVaultResponseSupport);

                final ResultActions result = this.mockMvc.perform(get("/api/v1/key/" + test)).andDo(print())
                                .andExpect(status().isOk());

                assertEquals("val", result.andReturn().getResponse().getContentAsString());
        }

        /** Tests {@link RequestController#getKey(String)} */
        @Test
        void testGetKekDev() throws Exception {
                final String test = "ewogICJrZWtfaWQiOiAiYXNkZjEyMzQiLAogICJrbXNfcHJvdmlkZXIiOiAiZGV2IiwKICAia21zX3Byb3ZpZGVyX3BhdGgiOiAiY3RybHBsYW5lL2FzaWEtcGFjaWZpYy9hcGFjLXRva3lvIiwKICAia21zX2FkZGl0aW9uYWxfcGFyYW1zIjogewogICAgICAic2VjcmV0X2lkIjogInRlc3RLZXkxIgogIH0KfQo=";

                final ResultActions result = this.mockMvc.perform(get("/api/v1/key/" + test)).andDo(print())
                                .andExpect(status().isOk());

                assertEquals("testkeycontent", result.andReturn().getResponse().getContentAsString());
        }

        /** Tests {@link RequestController#getKey(String)} */
        @Test
        void testGetKekNotFound() throws Exception {
                final String test = "ewogICJrZWtfaWQiOiAiYXNkZjEyMzQiLAogICJrbXNfcHJvdmlkZXIiOiAidmF1bHQiLAogICJrbXNfcHJvdmlkZXJfcGF0aCI6ICJjdHJscGxhbmUvYXNpYS1wYWNpZmljL2FwYWMtdG9reW8iLAogICJrbXNfYWRkaXRpb25hbF9wYXJhbXMiOiB7CiAgICAgICJzZWNyZXRfaWQiOiAidGVzdEtleTEiCiAgfQp9Cg==";

                when(mockVaultKeyResponse.getData()).thenReturn(Map.of("key", "val"));
                when(mockVaultResponseSupport.getRequiredData()).thenReturn(mockVaultKeyResponse);
                when(this.mockVaultServer.getKmsResponse("test")).thenReturn(null);

                this.mockMvc.perform(get("/api/v1/key/" + test)).andDo(print())
                                .andExpect(status().isNotFound());
        }
}
