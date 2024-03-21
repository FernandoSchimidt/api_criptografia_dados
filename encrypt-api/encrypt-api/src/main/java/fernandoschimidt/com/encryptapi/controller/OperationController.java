package fernandoschimidt.com.encryptapi.controller;

import fernandoschimidt.com.encryptapi.domain.operation.Operation;
import fernandoschimidt.com.encryptapi.dto.OperationDTO;
import fernandoschimidt.com.encryptapi.dto.OperationResponseDTO;
import fernandoschimidt.com.encryptapi.services.OperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/operation")
public class OperationController {

    private OperationService service;

    public OperationController(OperationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Operation> create(@RequestBody OperationDTO operationDTO, UriComponentsBuilder uriComponentsBuilder) {
        Operation newOperation = this.service.create(operationDTO);

        var uri = uriComponentsBuilder.path("/api/operation/{id}").buildAndExpand(newOperation.getId()).toUri();
        return ResponseEntity.created(uri).body(newOperation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationResponseDTO> read(@PathVariable Long id) {


        OperationResponseDTO operationResponseDTO = this.service.read(id);

        return ResponseEntity.ok(operationResponseDTO);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Operation> update(@PathVariable Long id, @RequestBody OperationDTO operation,UriComponentsBuilder uriComponentsBuilder){
        Operation newOperation = this.service.update(id,operation);

        var uri = uriComponentsBuilder.path("/api/operation/{id}").buildAndExpand(newOperation.getId()).toUri();
        return ResponseEntity.created(uri).body(newOperation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);

        return ResponseEntity.ok().build();
    }
}
