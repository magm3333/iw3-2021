package ar.edu.iua.iw3.modelo.cuentas;

import java.util.List;

import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

public interface IUserNegocio {

	public User cargar(int id) throws NegocioException, NoEncontradoException;
	public List<User> lista() throws NegocioException;
	public User agregar(User user) throws NegocioException, EncontradoException;
	public User modificar(User user) throws NegocioException, EncontradoException, NoEncontradoException;
	
	public User cargarPorNombreOEmail(String nombreOEmail) throws NegocioException, NoEncontradoException;
	
}
