package ma.enset.finprojectjavafx.dao;

import java.sql.SQLException;
import java.util.List;

public interface Doa <E, U>{
    void create(E e) throws SQLException;
    void delete(E e) throws SQLException;
    void update(E e) throws SQLException;
    List<E> findall() throws SQLException;
    E findById(U id) throws SQLException;
}
