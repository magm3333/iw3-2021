package ar.edu.iua.iw3.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.IProductoNegocio;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;

@RestController
public class ProductosRestController {

	@Autowired
	private IProductoNegocio productoNegocio;

	@GetMapping(value="/productos")
	public ResponseEntity<List<Producto>> listado() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listado(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
