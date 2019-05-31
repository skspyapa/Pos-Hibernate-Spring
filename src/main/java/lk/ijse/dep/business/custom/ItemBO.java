package lk.ijse.dep.business.custom;

import lk.ijse.dep.business.SuperBO;
import lk.ijse.dep.dto.ItemDTO;

public interface ItemBO extends SuperBO<ItemDTO,String> {

    String getMaxItemCode() throws Exception;
}
