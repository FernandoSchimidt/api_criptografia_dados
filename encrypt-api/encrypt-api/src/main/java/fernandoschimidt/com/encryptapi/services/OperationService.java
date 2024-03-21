package fernandoschimidt.com.encryptapi.services;


import fernandoschimidt.com.encryptapi.domain.operation.Operation;
import fernandoschimidt.com.encryptapi.domain.operation.exceptions.OperationNotFoundException;
import fernandoschimidt.com.encryptapi.dto.OperationDTO;
import fernandoschimidt.com.encryptapi.dto.OperationResponseDTO;
import fernandoschimidt.com.encryptapi.repository.OperationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationService {

    private OperationRepository repository;
    private EncryptService encryptService;

    public OperationService(OperationRepository repository, EncryptService encryptService) {
        this.repository = repository;
        this.encryptService = encryptService;
    }

    public Operation create(OperationDTO operationDTO) {
        Operation operation = new Operation();
        String userDocumentHashed = this.encryptService.encryptData(operationDTO.userDocument());
        String creditCardHashed = this.encryptService.encryptData(operationDTO.creditCardToken());

        operation.setCreditCardToken(creditCardHashed);
        operation.setUserDocument(userDocumentHashed);
        operation.setValue(operationDTO.operatioValue());

        this.repository.save(operation);
        return operation;
    }

    public OperationResponseDTO read(Long id) throws OperationNotFoundException {
        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        String userDocumentHashed = this.encryptService.decryptData(operation.getUserDocument());
        String creditCardHashed = this.encryptService.decryptData(operation.getCreditCardToken());

        OperationResponseDTO dto = new OperationResponseDTO(userDocumentHashed, creditCardHashed, operation.getValue(), operation.getId());

        return dto;
    }

    @Transactional
    public Operation update(Long id, OperationDTO data) {

        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        if (!data.creditCardToken().isEmpty()) {
            operation.setCreditCardToken(this.encryptService.encryptData(operation.getCreditCardToken()));
        }
        if (!data.userDocument().isEmpty()) {
            operation.setUserDocument(this.encryptService.encryptData(operation.getUserDocument()));
        }
        if (data.operatioValue() != null) {
            operation.setValue(data.operatioValue());
        }
//        repository.save(operation);
        return operation;
    }

    public void delete(Long id) {

        Operation operation = this.repository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        repository.delete(operation);

    }

}
