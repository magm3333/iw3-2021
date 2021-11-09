package ar.edu.iua.iw3.modelo.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.Rubro;
import ar.edu.iua.iw3.util.JsonUtiles;

public class ProductoJsonSerializer extends StdSerializer<Producto> {
	private static final long serialVersionUID = -6689462676540336093L;

	public ProductoJsonSerializer(Class<Producto> t) {
		super(t);
	}

	@Override
	public void serialize(Producto value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		{
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("descripcion", value.getDescripcion());
			gen.writeStringField("descripcionExtendida", value.getDescripcionExtendida());
			gen.writeNumberField("precio", value.getPrecio());
			gen.writeNumberField("precioConDescuento", value.getPrecio()*0.5);
			gen.writeBooleanField("enStock", value.isEnStock());
			gen.writeStringField("codigoExterno", value.getCodigoExterno());
			{ // Rubro
				String rubroStr = JsonUtiles.getObjectMapper(Rubro.class, new RubroJsonSerializer(Rubro.class), null)
						.writeValueAsString(value.getRubro());
				gen.writeFieldName("rubro");
				gen.writeRawValue(rubroStr);
			}
		}
		gen.writeEndObject();

	}

}
