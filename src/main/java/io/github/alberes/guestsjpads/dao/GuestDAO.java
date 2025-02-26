package io.github.alberes.guestsjpads.dao;

import io.github.alberes.guestsjpads.domains.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
@Slf4j
public class GuestDAO {

    @Autowired
    @Qualifier("drDataSource")
    private DataSource drDataSource;

    private static final String INSERT = "INSERT INTO guest(legal_entity_number, name, birthday, last_update_date, creation_date) values(?, ?, ?, ?, ?)";

    private static final String SELECT = "SELECT * FROM guest WHERE legal_entity_number = ?";

    private static final String UPDATE = "UPDATE guest SET name = ?, birthday = ?, last_update_date = ? WHERE legal_entity_number = ?";

    private static final String DELETE = "DELETE FROM guest where legal_entity_number = ?";

    public Guest save(Guest guest) {
        try (Connection connection = drDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, guest.getLegalEntityNumber());
            preparedStatement.setString(2, guest.getName());
            preparedStatement.setDate(3, Date.valueOf(guest.getBirthday()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(guest.getLastUpdateDate()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(guest.getCreationDate()));
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                connection.rollback();
                log.error("More than one Guest was inserted " + rows + " " + guest.getLegalEntityNumber());
                throw new RuntimeException("Could not save " + guest.getLegalEntityNumber());
            }
        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
            sqle.printStackTrace();
            throw new RuntimeException(sqle);
        }
        return guest;
    }

    public Guest find(String legalEntityNumber) {
        Guest guest = null;
        try (Connection connection = drDataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, legalEntityNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                guest = new Guest();
                guest.setLegalEntityNumber(resultSet.getString("legal_entity_number"));
                guest.setName(resultSet.getString("name"));
                guest.setBirthday(resultSet.getDate("birthday").toLocalDate());
                guest.setLastUpdateDate(resultSet.getTimestamp("last_update_date").toLocalDateTime());
                guest.setCreationDate(resultSet.getTimestamp("creation_date").toLocalDateTime());
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            log.error(sqle.getMessage());
            throw new RuntimeException(sqle);
        }
        return guest;
    }

    public void update(Guest guest){
        try(Connection connection = drDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, guest.getName());
            preparedStatement.setDate(2, Date.valueOf(guest.getBirthday()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(guest.getLastUpdateDate()));
            preparedStatement.setString(4, guest.getLegalEntityNumber());
            int rows = preparedStatement.executeUpdate();
            if(rows != 1){
                connection.rollback();
                log.error("More than one Guest was updated " + rows + " " + guest.getLegalEntityNumber());
                throw new RuntimeException("Could not update guest with legalEntityNumber " +
                        guest.getLegalEntityNumber());
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new RuntimeException(sqle);
        }
    }

    public void delete(String legalEntityNumber){
        try(Connection connection = drDataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, legalEntityNumber);
            int rows = preparedStatement.executeUpdate();
            if(rows != 1){
                log.error("More than one Guest was deleted " + rows + " " + legalEntityNumber);
                throw new RuntimeException("Could not delete " + legalEntityNumber);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            log.error(sqle.getMessage());
            throw new RuntimeException(sqle);
        }
    }

}
