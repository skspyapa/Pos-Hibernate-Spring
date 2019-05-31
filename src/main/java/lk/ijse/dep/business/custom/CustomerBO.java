package lk.ijse.dep.business.custom;

import lk.ijse.dep.business.SuperBO;
import lk.ijse.dep.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends SuperBO<CustomerDTO,String> {
     List<CustomerDTO> getCustomerId() throws Exception;

     String getMaxCustId() throws Exception;
}
