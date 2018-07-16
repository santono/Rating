package rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.UStepDAO;
import rating.domain.UStepEntity;
import rating.dto.ItemErrorDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class UStepService {
    @Autowired
    private UStepDAO uStepDAO;
    public UStepEntity getById(final int wantedId) {
           return uStepDAO.getById(wantedId);
    }

    public List<UStepEntity> getAll() {
        return uStepDAO.getAll();
    }

    public void saveRecord(UStepEntity uStepEntity) {
         uStepDAO.saveRecord(uStepEntity);
    }

    public void deleteRecord(final int wantedId) {
        uStepDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final UStepEntity uStepEntity) {
        uStepDAO.insertRecord(uStepEntity);
    }

    public List<ItemErrorDTO> getAllForDTO() {
        List<ItemErrorDTO> uStepsList = new ArrayList<ItemErrorDTO>();
        for (UStepEntity step:getAll()) {
            ItemErrorDTO item=new ItemErrorDTO(
                    step.getId(),
                    step.getName(),
                    step.getShortName()
            );
            uStepsList.add(item);
        }
        return uStepsList;
    }

}
