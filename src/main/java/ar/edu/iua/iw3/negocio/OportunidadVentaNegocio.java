package ar.edu.iua.iw3.negocio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.iua.iw3.modelo.OportunidadVenta;
import ar.edu.iua.iw3.modelo.persistencia.OportunidadVentaRepository;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.util.MensajeRespuesta;
import ar.edu.iua.iw3.util.RespuestaGenerica;

@Service
public class OportunidadVentaNegocio implements IOportunidadVentaNegocio {

	@Autowired
	private IProductoNegocio productoService;
	
	@Autowired
	private OportunidadVentaRepository oportunidadVentaDAO;
	
	@Override
	public RespuestaGenerica<OportunidadVenta> recibir(OportunidadVenta oportunidadVenta) throws NegocioException {
		MensajeRespuesta m=new MensajeRespuesta();
		RespuestaGenerica<OportunidadVenta> r = new RespuestaGenerica<OportunidadVenta>(oportunidadVenta, m);
		
		String mensajeCheck=oportunidadVenta.checkBasicData();
		if(mensajeCheck!=null) {
			m.setCodigo(-1);
			m.setMensaje(mensajeCheck);
			return r;
		}
		try {
			oportunidadVenta.setProducto(productoService.asegurarProducto(oportunidadVenta.getProducto()));
			oportunidadVenta.setFechaHora(new Date());
			oportunidadVenta.setConcretada(false);
			oportunidadVentaDAO.save(oportunidadVenta);
		} catch (Exception e) {
			throw new NegocioException(e);
		}
		return r;
	}

	@Override
	public List<OportunidadVenta> lista() throws NegocioException {
		try {
			return oportunidadVentaDAO.findAll(Sort.by("concreatada").and(Sort.by("fechaHora")));
		} catch (Exception e) {
			throw new NegocioException(e);
		}
	}

}
