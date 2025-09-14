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
class DefaultAnchorageTransactionNormalizationServiceTest {

    @InjectMocks
    private DefaultAnchorageTransactionNormalizationService service; 
     
         private NormalizedTransaction transaction;

    @Mock
    private AnchorageService anchorageService;  


    @BeforeEach
    void setUp() {
        transaction = NormalizedTransaction.builder()
                .vaultId("vault-123")
                .accountName("OldName")
                .build();
    }

    @Test
    void testMapWithVaultAccountName_NoVaultId_ReturnsOriginal() {
 
        NormalizedTransaction txWithoutVault = NormalizedTransaction.builder()
                .vaultId(null)
                .accountName("OriginalName")
                .build();

 
        StepVerifier.create(service.mapWithVaultAccountName(txWithoutVault))
                .expectNextMatches(result -> result.getAccountName().equals("OriginalName"))
                .verifyComplete();
    }

    @Test
    void testMapWithVaultAccountName_VaultFound() {
   
        AnchorageVaultData data = new AnchorageVaultData("vault-123", "NewAccountName");
        AnchorageVaultResponse vaultResponse = new AnchorageVaultResponse(data);

        when(anchorageService.getVault("vault-123")).thenReturn(Mono.just(vaultResponse));

        
        StepVerifier.create(service.mapWithVaultAccountName(transaction))
                .expectNextMatches(result ->
                        result.getVaultId().equals("vault-123") &&
                        result.getAccountName().equals("NewAccountName"))
                .verifyComplete();

        verify(anchorageService, times(1)).getVault("vault-123");
    }

    @Test
    void testMapWithVaultAccountName_VaultNotFound_ReturnsOriginal() {
        
        when(anchorageService.getVault("vault-123")).thenReturn(Mono.empty());
 
        StepVerifier.create(service.mapWithVaultAccountName(transaction))
                .expectNextMatches(result ->
                        result.getVaultId().equals("vault-123") &&
                        result.getAccountName().equals("OldName"))
                .verifyComplete();

        verify(anchorageService, times(1)).getVault("vault-123");
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
// class DefaultAnchorageTransactionNormalizationServiceTest {

//     @InjectMocks
//     private DefaultAnchorageTransactionNormalizationService service; 
//     // <-- replace with your actual class name

//     @Mock
//     private AnchorageService anchorageService; // dependency used in getVault()

//     private NormalizedTransaction transaction;

//     @BeforeEach
//     void setUp() {
//         transaction = NormalizedTransaction.builder()
//                 .vaultId("vault-123")
//                 .accountName("OldName")
//                 .build();
//     }

//     @Test
//     void testMapWithVaultAccountName_NoVaultId_ReturnsOriginal() {
//         // Arrange
//         NormalizedTransaction txWithoutVault = NormalizedTransaction.builder()
//                 .vaultId(null)
//                 .accountName("OriginalName")
//                 .build();

//         // Act & Assert
//         StepVerifier.create(service.mapWithVaultAccountName(txWithoutVault))
//                 .expectNextMatches(result -> result.getAccountName().equals("OriginalName"))
//                 .verifyComplete();
//     }

//     @Test
//     void testMapWithVaultAccountName_VaultFound() {
//         // Arrange
//         AnchorageVaultData data = new AnchorageVaultData("vault-123", "NewAccountName");
//         AnchorageVaultResponse vaultResponse = new AnchorageVaultResponse(data);

//         when(anchorageService.getVault("vault-123")).thenReturn(Mono.just(vaultResponse));

//         // Act & Assert
//         StepVerifier.create(service.mapWithVaultAccountName(transaction))
//                 .expectNextMatches(result ->
//                         result.getVaultId().equals("vault-123") &&
//                         result.getAccountName().equals("NewAccountName"))
//                 .verifyComplete();

//         verify(anchorageService, times(1)).getVault("vault-123");
//     }

//     @Test
//     void testMapWithVaultAccountName_VaultNotFound_ReturnsOriginal() {
//         // Arrange
//         when(anchorageService.getVault("vault-123")).thenReturn(Mono.empty());

//         // Act & Assert
//         StepVerifier.create(service.mapWithVaultAccountName(transaction))
//                 .expectNextMatches(result ->
//                         result.getVaultId().equals("vault-123") &&
//                         result.getAccountName().equals("OldName"))
//                 .verifyComplete();

//         verify(anchorageService, times(1)).getVault("vault-123");
//     }
// }
