package com.example.coche.service.impl;

import com.example.coche.exception.CocheNoEncontradoException;
import com.example.coche.model.Coche;
import com.example.coche.repository.CocheRepository;
import com.example.coche.service.CocheService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CocheServiceImpl implements CocheService {

    private final CocheRepository cocheRepository;

    public CocheServiceImpl(CocheRepository cocheRepository) {
        this.cocheRepository = cocheRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coche> listarTodos() {
        return cocheRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Coche buscarPorId(Long id) {
        return cocheRepository.findById(id)
                .orElseThrow(() -> new CocheNoEncontradoException(id));
    }

    @Override
    public Coche guardar(Coche coche) {
        return cocheRepository.save(coche);
    }

    @Override
    public void eliminar(Long id) {
        if (!cocheRepository.existsById(id)) {
            throw new CocheNoEncontradoException(id);
        }
        cocheRepository.deleteById(id);
    }
}
