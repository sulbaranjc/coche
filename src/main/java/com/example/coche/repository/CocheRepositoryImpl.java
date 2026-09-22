package com.example.coche.repository;

import com.example.coche.model.Coche;
import com.example.coche.model.Combustible;
import com.example.coche.model.Transmision;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CocheRepositoryImpl implements CocheRepository {

    private static final String SQL_SELECT_BASE =
            "SELECT id, marca, modelo, matricula, anio_fabricacion, color, precio, kilometraje, combustible, transmision "
                    + "FROM coche";

    private final JdbcTemplate jdbcTemplate;

    public CocheRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Coche> findAll() {
        String sql = SQL_SELECT_BASE + " ORDER BY id";
        return jdbcTemplate.query(sql, cocheRowMapper());
    }

    @Override
    public Optional<Coche> findById(Long id) {
        String sql = SQL_SELECT_BASE + " WHERE id = ?";
        List<Coche> resultado = jdbcTemplate.query(sql, cocheRowMapper(), id);
        return resultado.stream().findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM coche WHERE id = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return total != null && total > 0;
    }

    @Override
    public boolean existsByMatricula(String matricula) {
        String sql = "SELECT COUNT(*) FROM coche WHERE matricula = ?";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class, matricula);
        return total != null && total > 0;
    }

    @Override
    public Coche save(Coche coche) {
        if (coche.getId() == null) {
            return insertar(coche);
        }
        return actualizar(coche);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM coche WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Coche insertar(Coche coche) {
        String sql = "INSERT INTO coche "
                + "(marca, modelo, matricula, anio_fabricacion, color, precio, kilometraje, combustible, transmision) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            establecerParametros(ps, coche);
            return ps;
        }, keyHolder);

        coche.setId(keyHolder.getKey().longValue());
        return coche;
    }

    private Coche actualizar(Coche coche) {
        String sql = "UPDATE coche SET "
                + "marca = ?, modelo = ?, matricula = ?, anio_fabricacion = ?, color = ?, "
                + "precio = ?, kilometraje = ?, combustible = ?, transmision = ? "
                + "WHERE id = ?";

        jdbcTemplate.update(sql,
                coche.getMarca(),
                coche.getModelo(),
                coche.getMatricula(),
                coche.getAnioFabricacion(),
                coche.getColor(),
                coche.getPrecio(),
                coche.getKilometraje(),
                coche.getCombustible().name(),
                coche.getTransmision().name(),
                coche.getId());

        return coche;
    }

    private void establecerParametros(PreparedStatement ps, Coche coche) throws SQLException {
        ps.setString(1, coche.getMarca());
        ps.setString(2, coche.getModelo());
        ps.setString(3, coche.getMatricula());
        ps.setInt(4, coche.getAnioFabricacion());
        ps.setString(5, coche.getColor());
        ps.setBigDecimal(6, coche.getPrecio());
        ps.setInt(7, coche.getKilometraje());
        ps.setString(8, coche.getCombustible().name());
        ps.setString(9, coche.getTransmision().name());
    }

    private RowMapper<Coche> cocheRowMapper() {
        return (rs, rowNum) -> {
            Coche coche = new Coche();
            coche.setId(rs.getLong("id"));
            coche.setMarca(rs.getString("marca"));
            coche.setModelo(rs.getString("modelo"));
            coche.setMatricula(rs.getString("matricula"));
            coche.setAnioFabricacion(rs.getInt("anio_fabricacion"));
            coche.setColor(rs.getString("color"));
            coche.setPrecio(rs.getBigDecimal("precio"));
            coche.setKilometraje(rs.getInt("kilometraje"));
            coche.setCombustible(Combustible.valueOf(rs.getString("combustible")));
            coche.setTransmision(Transmision.valueOf(rs.getString("transmision")));
            return coche;
        };
    }
}
