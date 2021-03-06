package ar.edu.iua.iw3.negocio;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.eventos.ProductoEvent;
import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.Rubro;
import ar.edu.iua.iw3.modelo.persistencia.ProductoRepository;
import ar.edu.iua.iw3.modelo.persistencia.RubroRepository;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@Service
//@Configuration
public class ProductoNegocio implements IProductoNegocio {
	private Logger log = LoggerFactory.getLogger(ProductoNegocio.class);
	@Autowired
	private ProductoRepository productoDAO;

	@Autowired
	private RubroRepository rubroDAO;

	// IoC
	// A y B
	// A tiene el control <-- programadora Date fecha=new Date()
	// B <- Spring

	@Override
	public List<Producto> listado() throws NegocioException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Producto agregar(Producto producto) throws NegocioException, EncontradoException {
		try {
			cargar(producto.getId());
			throw new EncontradoException("Ya existe un producto con id=" + producto.getId());
		} catch (NoEncontradoException e) {
		}
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}

	}

	@Override
	public Producto cargar(long id) throws NegocioException, NoEncontradoException {
		Optional<Producto> o;
		try {
			o = productoDAO.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
		if (!o.isPresent()) {
			throw new NoEncontradoException("No se encuentra el producto con id=" + id);
		}
		return o.get();

	}

	@Override
	public Producto modificar(Producto producto) throws NegocioException, NoEncontradoException {

		Producto old = cargar(producto.getId());
		if (old.getPrecio() < producto.getPrecio() * .9) {
			generaEvento(producto, ProductoEvent.Tipo.SUBE_PRECIO);
		}

		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Autowired
	private ApplicationEventPublisher appEventPublisher;

	private void generaEvento(Producto producto, ProductoEvent.Tipo tipo) {
		appEventPublisher.publishEvent(new ProductoEvent(producto, tipo));
	}

	@Override
	public void eliminar(long id) throws NegocioException, NoEncontradoException {
		cargar(id);

		try {
			productoDAO.deleteById(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public List<Rubro> listadoRubros() throws NegocioException {
		try {
			return rubroDAO.findAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new NegocioException(e);
		}
	}

	@Override
	public Producto cargar(String codigoExterno) throws NegocioException, NoEncontradoException {
		Optional<Producto> op;
		try {
			op = productoDAO.findFirstByCodigoExterno(codigoExterno);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
		if (!op.isPresent()) {
			throw new NoEncontradoException(
					"El producto con c??digo externo '" + codigoExterno + "', no se encuentra en la BD");
		}
		return op.get();
	}

	@Override
	public Producto asegurarProducto(Producto producto) throws NegocioException {
		Producto p = null;
		try {
			p = cargar(producto.getCodigoExterno());
			p.setPrecio(producto.getPrecio());
			p.setDescripcion(producto.getDescripcion());
			// Colocar aqu?? los datos recibidos del exterior que no sean opcionales
		} catch (NoEncontradoException e) {
			p = new Producto(producto);
		}
		try {
			p=productoDAO.save(p);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
		return p;
	}

	// @Bean
	// public IProductoNegocio getProductoNegocio() {
	// return new ProductoNegocio();
	// }

}
