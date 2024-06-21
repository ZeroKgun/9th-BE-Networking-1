package com.cotato.networking1.repository;


import com.cotato.networking1.entity.Property;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Property> properties) {
        String sql = "insert into property (zip_code, road_name_address, land_lot_name_address) values (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                properties,
                properties.size(),
                (PreparedStatement ps, Property property) -> {
                    ps.setString(1, property.getZipCode());
                    ps.setString(2, property.getRoadNameAddress());
                    ps.setString(3, property.getLandLotNameAddress());
                });
    }
}
