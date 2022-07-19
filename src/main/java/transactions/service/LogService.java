package transactions.service;

import transactions.model.Log;
import transactions.repository.LogRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Transactional
    public void add(Log log){
        logRepository.save(log);
    }

    public List<Log> getAll(){
        return logRepository.findAll();
    }
}
