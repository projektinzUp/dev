package ProjektInz.RESTAPI.Service;

import ProjektInz.RESTAPI.repository.CodeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseSupport {
    @Autowired
    CodeEntityRepository codeEntityRepository;

    public void codesToRemove() {
        if (codeEntityRepository.getCodes().size() > 3) {
            codeEntityRepository.deleteOlderThenTimestamp(codeEntityRepository.getCodeObject());
        }
    }
}
