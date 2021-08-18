package ar.edu.iua.iw3.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.persistencia.ProductoRepository;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;

@Service
//@Configuration
public class ProductoNegocio implements IProductoNegocio {

	@Autowired
	private ProductoRepository productoDAO;

	// IoC
	// A y B  
	// A tiene el control <-- programadora Date fecha=new Date()
	// B <- Spring 
 	
	@Override
	public List<Producto> listado() throws NegocioException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}
	
	//@Bean
	//public IProductoNegocio getProductoNegocio() {
	//	return new ProductoNegocio();
	//}

}
