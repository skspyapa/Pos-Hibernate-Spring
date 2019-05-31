package lk.ijse.dep.business;

import java.util.List;

public interface SuperBO<T,ID> {
     List<T> getAll() throws Exception;

     boolean save(T dto) throws Exception;

     boolean remove(ID  dtoId) throws Exception;

     boolean update(T dtoId) throws Exception;

     T get(ID dtoId) throws Exception;
}
