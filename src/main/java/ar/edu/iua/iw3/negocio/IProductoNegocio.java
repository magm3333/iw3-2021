package ar.edu.iua.iw3.negocio;

import java.util.List;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;

public interface IProductoNegocio {
	public List<Producto> listado() throws NegocioException;
}
