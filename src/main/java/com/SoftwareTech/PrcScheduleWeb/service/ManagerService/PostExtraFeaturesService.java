package com.SoftwareTech.PrcScheduleWeb.service.ManagerService;

import com.SoftwareTech.PrcScheduleWeb.dto.ManagerServiceDto.ReqAddSubject;
import com.SoftwareTech.PrcScheduleWeb.model.Subject;
import com.SoftwareTech.PrcScheduleWeb.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostExtraFeaturesService {
    @Autowired
    private final SubjectRepository subjectRepository;

    public void addSubject(ReqAddSubject subjectObject) {
        if (subjectRepository.findByIdOrSubjectName(
            subjectObject.getSubjectId(), subjectObject.getSubjectName()).isPresent()
        )
            throw new DuplicateKeyException("Subject is already existing");

        //--May throw SQLException
        subjectRepository.save(Subject.builder()
            .subjectId(subjectObject.getSubjectId())
            .subjectName(subjectObject.getSubjectName())
            .creditsNumber(subjectObject.getCreditsNumber())
            .status(true)
            .build());
    }
}
