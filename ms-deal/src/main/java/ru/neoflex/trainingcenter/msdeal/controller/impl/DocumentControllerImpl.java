package ru.neoflex.trainingcenter.msdeal.controller.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.azobnina.liblog.InfoLog;
import ru.neoflex.trainingcenter.msdeal.controller.DocumentController;
import ru.neoflex.trainingcenter.msdeal.service.DocumentService;

import java.util.UUID;

@InfoLog
@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;

    @Override
    public ResponseEntity<Void> send(UUID applicationId)  {

        documentService.send(applicationId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> sign(UUID applicationId)  {

        documentService.sign(applicationId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> code(UUID applicationId)  {

        documentService.code(applicationId);

        return ResponseEntity.ok().build();
    }
}
