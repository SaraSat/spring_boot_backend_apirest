package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(org.springframework.data.domain.Pageable pageable) {
		
		return clienteDao.findAll(pageable);
	}
	
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
	
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {

		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findbyId(Long id) {
	
		return clienteDao.findById(id).orElse(null);
	}





	

}
