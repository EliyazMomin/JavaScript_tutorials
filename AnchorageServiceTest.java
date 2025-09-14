import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnchorageServiceTest {

    @Mock
    private AnchorageService anchorageService;  

    @InjectMocks
    private MyService myService; 

    private AnchorageVaultResponse vaultResponse;

    @BeforeEach

    void setUp() {
        AnchorageVaultData data = new AnchorageVaultData("vault-123", "Test Account");
        vaultResponse = new AnchorageVaultResponse(data);
    }

    @Test
    void testGetVault_Found() {
       String vaultId = "vault-999";
        when(anchorageService.getVault("vault-123")).thenReturn(Mono.just(vaultResponse));

         
        StepVerifier.create(myService.getVault("vault-123"))
                .expectNextMatches(vault -> 
                        vault.getData().getVaultId().equals("vault-123") &&
                        vault.getData().getAccountName().equals("Test Account"))
                .verifyComplete();

        verify(anchorageService, times(1)).getVault("vault-123");
    }

    @Test
    void testGetVault_NotFound() {
        String vaultId = "vault-999";

    when(anchorageService.getVault(vaultId)).thenReturn(Mono.error(new DAException("ValutID has no data")));
 
    when(anchorageService.getVault(vaultId)).thenReturn(Mono.just(vaultResponse));

    StepVerifier.create(service.getVault(vaultId))   // <-- call the real method under test
            .expectErrorMatches(aex ->
                    aex instanceof DAException &&
                    aex.getMessage().contains("vaultId has no data"))
            .verify();

    verify(anchorageService, times(1)).getVault(vaultId);
    }
}








// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import reactor.core.publisher.Mono;
// import reactor.test.StepVerifier;

// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class AnchorageServiceTest {

//     @Mock
//     private AnchorageService anchorageService; // mocked dependency

//     @InjectMocks
//     private MyService myService; // the class containing getVault()

//     private AnchorageVaultResponse vaultResponse;

//     @BeforeEach
//     void setUp() {
//         AnchorageVaultData data = new AnchorageVaultData("vault-123", "Test Account");
//         vaultResponse = new AnchorageVaultResponse(data);
//     }

//     @Test
//     void testGetVault_Found() {
//         // Arrange
//         when(anchorageService.getVault("vault-123")).thenReturn(Mono.just(vaultResponse));

//         // Act & Assert
//         StepVerifier.create(myService.getVault("vault-123"))
//                 .expectNextMatches(vault -> 
//                         vault.getData().getVaultId().equals("vault-123") &&
//                         vault.getData().getAccountName().equals("Test Account"))
//                 .verifyComplete();

//         verify(anchorageService, times(1)).getVault("vault-123");
//     }

//     @Test
//     void testGetVault_NotFound() {
//         // Arrange
//         when(anchorageService.getVault("vault-999")).thenReturn(Mono.empty());

//         // Act & Assert
//         StepVerifier.create(myService.getVault("vault-999"))
//                 .expectErrorMatches(ex -> ex instanceof DAException &&
//                         ex.getMessage().contains("vaultId has no data"))
//                 .verify();

//         verify(anchorageService, times(1)).getVault("vault-999");
//     }
// }
