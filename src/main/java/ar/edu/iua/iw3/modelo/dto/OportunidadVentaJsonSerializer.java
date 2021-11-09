package ar.edu.iua.iw3.modelo.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ar.edu.iua.iw3.modelo.OportunidadVenta;
import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.Rubro;
import ar.edu.iua.iw3.util.JsonUtiles;

public class OportunidadVentaJsonSerializer extends StdSerializer<OportunidadVenta> {
	private static final long serialVersionUID = -6689462676540336093L;

	public OportunidadVentaJsonSerializer(Class<OportunidadVenta> t) {
		super(t);
	}

	@Override
	public void serialize(OportunidadVenta value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		{
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("clientePotencial", value.getClientePotencial());
			gen.writeStringField("mailCliente", value.getMailCliente());
			gen.writeNumberField("cantidad", value.getCantidad());
			gen.writeStringField("comentarios", value.getComentarios());
			gen.writeObjectField("fechaHora", value.getFechaHora());
			{ // Producto
				String productoStr = JsonUtiles.getObjectMapper(Producto.class, new ProductoJsonSerializer(Producto.class), null)
						.writeValueAsString(value.getProducto());
				gen.writeFieldName("producto");
				gen.writeRawValue(productoStr);
			}
		}
		gen.writeEndObject();

	}

}
